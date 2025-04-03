package cn.it.lottery.domain.strategy.service.algorithm.impl;

import cn.it.lottery.domain.strategy.model.vo.AwardRateInfo;
import cn.it.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 38309
 */ //概率分配 老A个数(概率)/新总商品个数(总概率)=新A概率
//概率匹配 Random 在A概率内,中奖
@Component("rangeMatchAlgorithm")
public class RangeMatchAlgorithm extends BaseAlgorithm {
    //数据在哪 >> awardRateInfoMap(StrategyId awardRateInfo)
    //怎么拿到A概率 awardRateInfoMap.get(awardRate);>>>它是Map so 只能通过key去拿awardRateInfo
    @Override
    public String randomRun(Long strategyId,List<String> excludeAwardIds){
        BigDecimal differenceDenominator =BigDecimal.ZERO;
        List<AwardRateInfo> awardRateInfoList=awardRateInfoMap.get(strategyId);
        //新AwardRateInfo
        List<AwardRateInfo> differenceAwardRateList= new ArrayList<>();
        //怎么拿到总个数 for() 遍历新总概率=A0.2+C0.5
        for(AwardRateInfo awardRateInfo:awardRateInfoList){
            //加一个判断,如果AwardRateInfo已经被抛弃,则跳出循环
            if(excludeAwardIds.contains(awardRateInfo.getAwardId())){
                continue;
            }
            //把新AwardRateInfo遍历到新数组
            differenceAwardRateList.add(awardRateInfo);
            differenceDenominator=differenceDenominator.add(awardRateInfo.getAwardRate());
        }

        // 前置判断：奖品列表为0，返回NULL
        if (differenceAwardRateList.size() == 0) {
            return null;
        }

        // 前置判断：奖品列表为1，直接返回
        if (differenceAwardRateList.size() == 1) {
            return differenceAwardRateList.get(0).getAwardId();
        }

        // 获取随机概率值
        int randomVal = this.generateSecureRandomIntCode(100);

        String awardId = null;
        int cursorVal = 0;

        //概率分配>>老A个数(概率)/新总商品个数(总概率)=新A概率
        for(AwardRateInfo awardRateInfo:differenceAwardRateList){
            int rateVal=awardRateInfo.getAwardRate().divide(differenceDenominator,2,BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            // 概率匹配 Random 在A概率内,中奖
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = awardRateInfo.getAwardId();
                break;
            }
            //else 判断,使其无限走if
            cursorVal += rateVal;
        }

        return awardId;
    }


}
