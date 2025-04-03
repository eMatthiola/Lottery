package cn.it.lottery.domain.activity.service.partake;

import cn.it.lottery.common.Constants;
import cn.it.lottery.common.Result;
import cn.it.lottery.domain.activity.model.req.PartakeReq;
import cn.it.lottery.domain.activity.model.res.PartakeResult;
import cn.it.lottery.domain.activity.model.res.StockResult;
import cn.it.lottery.domain.activity.model.vo.ActivityBillVO;
import cn.it.lottery.domain.activity.model.vo.UserTakeActivityVO;
import cn.it.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName BaseActivityPartake
 * @Description 活动领取模板抽象类
 * @Author Matthiola
 * @Date 2023/9/30 14:54
 */

public abstract class BaseActivityPartake extends ActivityPartakeSupport implements IActivityPartake{

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Override
    public PartakeResult doPartake(PartakeReq req) {

        // 1. 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
        UserTakeActivityVO userTakeActivityVO = this.queryNoConsumedTakeActivityOrder(req.getActivityId(), req.getuId());
        if (null != userTakeActivityVO) {
            return buildPartakeResult(userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId(),Constants.ResponseCode.NOT_CONSUMED_TAKE);
        }


        // 2. 查询活动账单,get activityBillVO
        ActivityBillVO activityBillVO = super.queryActivityBillVO(req);

        // 3. 活动信息校验处理【活动库存、状态、日期、个人参与次数】,check activityBillVO
        //todo open
//        Result result = this.checkActivityBill(req, activityBillVO);
//        if(!Constants.ResponseCode.SUCCESS.getCode().equals(result.getCode())){
//           return new PartakeResult(result.getCode(), result.getInfo());
//        }

        // 4. 扣减活动库存，通过Redis【活动库存扣减编号，作为锁的Key，缩小颗粒度】 Begin
        StockResult subtractionActivityResult = this.subtractionActivityStockByRedis(req.getuId(),req.getActivityId(),activityBillVO.getStockCount());
        if(!Constants.ResponseCode.SUCCESS.getCode().equals((subtractionActivityResult.getCode()))){
            return new PartakeResult(subtractionActivityResult.getCode(), subtractionActivityResult.getInfo());
        }

        //5. 领取活动信息【个人用户把活动信息写入到用户表】 to user_take_activity table
        Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        Result grabActivity = this.grabActivity(req, activityBillVO, takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabActivity.getCode())) {
            this.recoverActivityCacheStockByRedis(req.getActivityId(),subtractionActivityResult.getStockKey(),grabActivity.getCode());
            return new PartakeResult(grabActivity.getCode(), grabActivity.getInfo());
        }

        // 6. 扣减活动库存，通过Redis del锁
        this.recoverActivityCacheStockByRedis(req.getActivityId(), subtractionActivityResult.getStockKey(), Constants.ResponseCode.SUCCESS.getCode());
        return buildPartakeResult(activityBillVO.getStrategyId(), takeId, activityBillVO.getStockCount(),subtractionActivityResult.getStockSurplusCount(),Constants.ResponseCode.SUCCESS);
    }

    /**
     * 活动信息校验处理，把活动库存、状态、日期、个人参与次数
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @return 校验结果
     */
    protected abstract Result checkActivityBill(PartakeReq partake, ActivityBillVO bill);

    /**
     * 扣减活动库存
     *
     * @param req 参与活动请求
     * @return 扣减结果
     */
    protected abstract Result subtractionActivityStock(PartakeReq req);

    /**
     * 领取活动信息
     *
     * @param req 参与活动请求
     * @param activityBillVO 活动账单
     * @return 领取结果
     */
    protected abstract Result grabActivity(PartakeReq req, ActivityBillVO activityBillVO,Long takeId);

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    protected abstract UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 封装结果【返回的策略ID，用于继续完成抽奖步骤】
     *
     * @param strategyId 策略ID
     * @param takeId     领取ID
     * @param code       状态码
     * @return 封装结果
     */
    private PartakeResult buildPartakeResult(Long strategyId, Long takeId, Constants.ResponseCode code) {
        PartakeResult partakeResult = new PartakeResult(code.getCode(), code.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        return partakeResult;
    }

    /**
     * 封装结果【返回的策略ID，用于继续完成抽奖步骤】
     *
     * @param strategyId        策略ID
     * @param takeId            领取ID
     * @param stockCount        库存
     * @param stockSurplusCount 剩余库存
     * @param code              状态码
     * @return 封装结果
     */
    private PartakeResult buildPartakeResult(Long strategyId, Long takeId, Integer stockCount, Integer stockSurplusCount, Constants.ResponseCode code) {
        PartakeResult partakeResult = new PartakeResult(code.getCode(),code.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        partakeResult.setStockCount(stockCount);
        partakeResult.setStockSurplusCount(stockSurplusCount);
        return partakeResult;
    }


    /**
     * 扣减活动库存，通过Redis
     *
     * @param uId        用户ID
     * @param activityId 活动号
     * @param stockCount 总库存
     * @return 扣减结果
     */
    protected abstract StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount);


    /**
     * 恢复活动库存，通过Redis 【如果非常异常，则需要进行缓存库存恢复，只保证不超卖的特性，所以不保证一定能恢复占用库存，另外最终可以由任务进行补偿库存】
     *
     * @param activityId 活动ID
     * @param tokenKey   分布式 KEY 用于清理
     * @param code       状态
     */
    protected abstract void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code);
}
