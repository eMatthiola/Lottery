package cn.it.lottery.domain.strategy.repository;

import cn.it.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.it.lottery.domain.strategy.model.vo.AwardBriefVO;
import java.util.List;

public interface IStrategyRepository {

    //拿到StrategyRich和AwardRateInfo
//    StrategyRich strategyRich(Long strategyId, Strategy strategy, List<StrategyDetail> strategyDetailList);
//    AwardRateInfo awardRateInfo(String awardId, BigDecimal awardRate);

    //查询策略信息.查询奖品,查询无库存的奖品,减少库存.
    StrategyRich queryStrategyRich(Long strategyId);
    AwardBriefVO queryAwardInfo(String awardId);

    List<String> queryNoStockStrategyAwardList(Long strategyId);

    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return           扣减结果
     */
    boolean deductStock(Long strategyId,String awardId);

}
