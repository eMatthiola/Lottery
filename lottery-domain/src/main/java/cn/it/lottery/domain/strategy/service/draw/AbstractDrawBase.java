package cn.it.lottery.domain.strategy.service.draw;


import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.it.lottery.domain.strategy.model.req.DrawReq;
import cn.it.lottery.domain.strategy.model.res.DrawResult;
import cn.it.lottery.domain.strategy.model.vo.*;
import cn.it.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec {
    // 1. 获取抽奖策略
    // 2. 校验抽奖策略是否已经初始化到内存
    // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
    // 4. 执行抽奖算法
    // 5. 包装中奖结果
    private Logger logger=LoggerFactory.getLogger(AbstractDrawBase.class);


    @Override
    public DrawResult doDrawExec(DrawReq req) {
        // 1. 获取抽奖策略
        StrategyRich strategyRich =super.queryStrategyRich(req.getStrategyId());
//        Long strategyId=strategyRich.getStrategyId() 可以直接通过req.getStrategyId();

        // 2. 校验抽奖策略是否已经初始化到内存
        StrategyBriefVO strategy =strategyRich.getStrategy();
//        Integer strategyMode=strategyRich.getStrategy();  为了拿到strategy.getStrategyMode()
        this.checkAndInitRateData(req.getStrategyId(),strategy.getStrategyMode(),strategyRich.getStrategyDetailList());

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<String> excludeAwardIds=this.queryExcludeAwardIds(req.getStrategyId());

        // 4. 执行抽奖算法
//        this.drawAlgorithm(req.getStrategyId(),IDrawAlgorithm drawAlgorithm,this.queryExcludeAwardId());
        String awardId=this.drawAlgorithm(req.getStrategyId(), drawAlgorithmMap.get(strategy.getStrategyMode()),excludeAwardIds);

        // 5. 包装中奖结果
        return buildDrawResult(req.getuId(), req.getStrategyId(), awardId, strategy);
    }


    /**
     * 校验抽奖策略是否已经初始化到内存
     *
     * @param strategyId         抽奖策略ID
     * @param strategyMode       抽奖策略模式
     * @param strategyDetailList 抽奖策略详情
     */
    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetailBriefVO> strategyDetailList) {

        //1.在缓存的就不要再初始化了
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);
        if (drawAlgorithm.isExistRateTuple(strategyId)) {return;}

        //2.添加奖品到awardRateInfoList
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetailBriefVO strategyDetail : strategyDetailList) {
            //awardRateInfoList(ConcurrentHashMap)
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }
        //3.初始化奖品信息>>key : values
        drawAlgorithm.initAward(strategyId, awardRateInfoList);

        //4.单项才走斐波那契 ,整体不用走斐波那契,到这里就停止!
        if(Constants.StrategyMode.ENTIERTY.getCode().equals(strategyMode)) {return;}

        //5.初始化元组>>awardRateInfoList>>rateTupleMap
        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据是含有业务逻辑的，所以需要由具体的实现方决定
     *
     * @param strategyId 策略ID
     * @return 排除的奖品ID集合
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法
     * private IDrawAlgorithm; 接口直接能作为方法参数传进来
     * @param strategyId      策略ID
     * @param drawAlgorithm   抽奖算法模型
     * @param excludeAwardIds 排除的抽奖ID集合
     * @return 中奖奖品ID
     */
    public abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm,List<String> excludeAwardIds);


    /**
     * 包装抽奖结果
     *
     * @param uId        用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID，null 情况：并发抽奖情况下，库存临界值1 -> 0，会有用户中奖结果为 null
     * @return 中奖结果
     */
    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId, StrategyBriefVO strategy) {
        if (null == awardId) {
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        AwardBriefVO award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardVO drawAwardInfo = new DrawAwardVO(uId, award.getAwardId(), award.getAwardType(), award.getAwardName(), award.getAwardContent());
        drawAwardInfo.setGrantDate(strategy.getGrantDate());
        drawAwardInfo.setGrantType(strategy.getGrantType());
        drawAwardInfo.setStrategyMode(strategy.getStrategyMode());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawResult(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }
}
