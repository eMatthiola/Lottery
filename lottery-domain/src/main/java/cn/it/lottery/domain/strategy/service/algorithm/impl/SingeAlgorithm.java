package cn.it.lottery.domain.strategy.service.algorithm.impl;

import cn.it.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;

//securityRandom >>hashcode>>if ==awardId Bingo
@Component("singeAlgorithm")
public class SingeAlgorithm extends BaseAlgorithm {
    @Override
    public String randomRun(Long strategyId, List<String> excludeAwardIds){
        // 获取策略对应的元祖
        String[] rateTuple=super.rateTupleMap.get(strategyId);
        assert rateTuple != null;

        // 随机索引
        int randomVal = this.generateSecureRandomIntCode(100);
        int idx = super.hashIdx(randomVal);

        // 返回结果
        String awardId=rateTuple[idx];

        // 如果中奖ID命中排除奖品列表，则返回NULL
        if (excludeAwardIds.contains(awardId)) {
            return null;
        }
        return awardId;
    }
}
