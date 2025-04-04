package cn.it.lottery.infrastructure.repository;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import cn.it.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import cn.it.lottery.domain.activity.model.req.PartakeReq;
import cn.it.lottery.domain.activity.model.res.StockResult;
import cn.it.lottery.domain.activity.model.vo.*;
import cn.it.lottery.domain.activity.respository.IActivityRepository;
import cn.it.lottery.infrastructure.dao.*;
import cn.it.lottery.infrastructure.po.*;
import cn.it.lottery.infrastructure.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 38309
 * @create 2023/9/17 16:27
 */
@Component
public class ActivityRepository implements IActivityRepository {
    private Logger logger = LoggerFactory.getLogger(ActivityRepository.class);
    @Resource
    private IActivityDao activityDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public void addActivity(ActivityVO activityVO) {
       //vo转po
        Activity req = new Activity();
        BeanUtils.copyProperties(activityVO,req);
        activityDao.insert(req);

        // 设置活动库存 KEY
        redisUtil.set(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityVO.getActivityId()), 0);
    }


    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award> req= new ArrayList<>();
        for (AwardVO awardVO : awardList) {
            Award award= new Award();
            BeanUtils.copyProperties(awardVO,award);
            req.add(award);
        }
        awardDao.insertList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy,req);
        strategyDao.insert(req);
    }

    @Override
    public void addStrategyDetail(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req= new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList) {
            StrategyDetail strategyDetail= new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO,strategyDetail);
            req.add(strategyDetail);
        }
        strategyDetailDao.insertList(req);
    }

    @Override
    public Boolean alterStatus(Long activityId, Enum<Constants.ActivityState> beforeState, Enum<Constants.ActivityState> afterState) {
        AlterStateVO alterStateVO = new AlterStateVO(activityId, ((Constants.ActivityState) beforeState).getCode(), ((Constants.ActivityState) afterState).getCode());
        int count = activityDao.alterState(alterStateVO);
        return 1==count;
    }

    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {


        // 查询活动信息
        Activity activity = activityDao.queryActivityById(req.getActivityId());

        // 从缓存中获取库存
        Object userStockCountObj = redisUtil.get(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(req.getActivityId()));

        // 查询领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setuId(req.getuId());
        userTakeActivityCountReq.setActivityId(req.getActivityId());
        UserTakeActivityCount  userTakeActivityCount= userTakeActivityCountDao.queryUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息TO activityBillVO
        ActivityBillVO activityBillVO = new ActivityBillVO();
        activityBillVO.setuId(req.getuId());
        activityBillVO.setActivityId(req.getActivityId());
        activityBillVO.setActivityName(activity.getActivityName());
        activityBillVO.setBeginDateTime(activity.getBeginDateTime());
        activityBillVO.setEndDateTime(activity.getEndDateTime());
        activityBillVO.setTakeCount(activity.getTakeCount());
        activityBillVO.setStockCount(activity.getStockCount());
        activityBillVO.setStockSurplusCount(null == userStockCountObj ? activity.getStockSurplusCount() : activity.getStockCount()-Integer.parseInt(String.valueOf(userStockCountObj)));
        activityBillVO.setState(activity.getState());
        activityBillVO.setStrategyId(activity.getStrategyId());
        activityBillVO.setUserTakeLeftCount(null == userTakeActivityCount ? null : userTakeActivityCount.getLeftCount());

        return activityBillVO;
    }

    @Override
    public int subtractionActivityStock(Long activityId) {
        return activityDao.subtractionActivityStock(activityId);
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {
        List<Activity> activityList = activityDao.scanToDoActivityList(id);
        List<ActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setState(activity.getState());
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount) {
        //  1. 获取抽奖活动库存 Key
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);  //lottery_activity_stock_count_activityId

        // 2. 扣减库存，目前stockKey占用库存数 1
        Integer stockUsedCount =(int) redisUtil.incr(stockKey, 1); //原理是计数器,初始为0,返回递增后的值

        // 3. 超出库存判断，进行恢复原始库存 1>0
        if (stockUsedCount>stockCount) {
            redisUtil.decr(stockKey, 1);
            return new StockResult(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }

        // 4. 以活动库存占用编号，生成对应加锁Key，细化锁的颗粒度
        String stockTokenKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(activityId, stockUsedCount); //activityId_1

        // 5. 使用 Redis.setNx 加一个分布式锁
        boolean lockToken = redisUtil.setNx(stockTokenKey, 350L);
        if (!lockToken) {
            logger.info("抽奖活动{}用户秒杀{}扣减库存，分布式锁失败：{}", activityId, uId, stockTokenKey);
            return new StockResult(Constants.ResponseCode.ERR_TOKEN.getCode(), Constants.ResponseCode.ERR_TOKEN.getInfo());
        }

        return new StockResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), stockTokenKey, stockCount-stockUsedCount);
    }

    @Override
    public void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        // 删除分布式锁 Key
        redisUtil.del(tokenKey);

    }

    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {

        //查询活动分页数据数量Queries the amount of active page data
        Long count = activityDao.queryActivityInfoCount(req);

        //查询活动分页数据列表
        List<Activity> activityList = activityDao.queryActivityInfoList(req);

        //put  activityList into activityVOList
        List<ActivityVO> activityVOList = new ArrayList<>();

        for (Activity activity : activityList) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setActivityId(activity.getActivityId());
            activityVO.setActivityName(activity.getActivityName());
            activityVO.setActivityDesc(activity.getActivityDesc());
            activityVO.setBeginDateTime(activity.getBeginDateTime());
            activityVO.setEndDateTime(activity.getEndDateTime());
            activityVO.setStockCount(activity.getStockCount());
            activityVO.setStockSurplusCount(activity.getStockSurplusCount());
            activityVO.setTakeCount(activity.getTakeCount());
            activityVO.setStrategyId(activity.getStrategyId());
            activityVO.setState(activity.getState());
            activityVO.setCreator(activity.getCreator());
            activityVO.setCreateTime(activity.getCreateTime());
            activityVO.setUpdateTime(activity.getUpdateTime());

            activityVOList.add(activityVO);
        }

        return new ActivityInfoLimitPageRich(count, activityVOList);
    }

}
