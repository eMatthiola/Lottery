package cn.it.lottery.domain.strategy.service.draw;

import cn.it.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.it.lottery.domain.strategy.model.vo.AwardBriefVO;
import cn.it.lottery.domain.strategy.repository.IStrategyRepository;


import javax.annotation.Resource;

/**
 * @Description 拿取数据
 * @author 38309
 */
public class DrawStrategySupport extends DrawConfig{
    @Resource
    protected IStrategyRepository strategyRepository;

   protected StrategyRich queryStrategyRich(Long strategyId){
        return strategyRepository.queryStrategyRich(strategyId);
    }

    protected AwardBriefVO queryAwardInfoByAwardId (String awardId){
       return strategyRepository.queryAwardInfo(awardId);
    }
}
