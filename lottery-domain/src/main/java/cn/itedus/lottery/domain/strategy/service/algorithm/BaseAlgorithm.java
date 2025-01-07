package cn.itedus.lottery.domain.strategy.service.algorithm;


import cn.itedus.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 38309
 */ //将1~100的奖品AwardId 通过hash处理,扔到128的数组里面
//Random 通过hash处理 与128数组匹配,等于AwardId中奖
public abstract class BaseAlgorithm implements IDrawAlgorithm {
    private  static int HASH_INCREMENT=0x61c88647;

    private final int RATE_TUPLE_LENGTH=128;
    //awardRateInfoMap于rateTupleMap区别是一个是否经过斐波那契
    protected Map<Long, List<AwardRateInfo>> awardRateInfoMap=new ConcurrentHashMap<>();
    //Long strategyId 可以看作"一次"记录操作.
    //本质是将List<AwardRateInfo>中的awardId 通过Hash斐波那契散列到String[]中去,同时转移Long strategyId
    protected Map<Long, String[]> rateTupleMap=new ConcurrentHashMap<>();
    @Override
    public void initAward(Long strategyId, List<AwardRateInfo> awardRateInfoList) {
        // 保存奖品概率信息
        awardRateInfoMap.put(strategyId, awardRateInfoList);
    }

    //哈希散列
    @Override
    public void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList){
        awardRateInfoMap.put(strategyId,awardRateInfoList);
        String[] rateTuple =rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]) ;

        int cursorVal = 0;
        //拿出List<AwardRateInfo>
        for(AwardRateInfo awardRateInfo:awardRateInfoList){
            int rateVal=awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();
            //使 hash[KEY] 与 awardId 对应
            //将每个概率的奖品的hash值都计算出来
            for(int i=cursorVal + 1; i<=(rateVal + cursorVal); i++ ){
                //  将Hash值放到数组中,成为数组的index
                rateTuple[hashIdx(i)]=awardRateInfo.getAwardId();
            }
            cursorVal += rateVal;
        }
    }




    /**
     * 斐波那契（Fibonacci）散列法，计算哈希索引下标值
     *
     * @param val 值
     * @return 索引
     */
    protected int hashIdx(int val){
       int hashCode =val*HASH_INCREMENT+HASH_INCREMENT;
       //新加入的放在链头，最先加入的放在链尾,与运算???
        return hashCode &(RATE_TUPLE_LENGTH-1);
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return rateTupleMap.containsKey(strategyId);
    }

    protected int generateSecureRandomIntCode(int bound){
        return new SecureRandom().nextInt(bound) + 1;
    }
}
