package cn.it.lottery.domain.award.service.goods;

import cn.it.lottery.domain.award.repository.IAwardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * @description: 配送货物基础共用类
 * @Author : 38309
 * @create 2023/9/12 14:18
 */
public class DistributionBase {
    protected Logger logger = LoggerFactory.getLogger(DistributionBase.class);

    @Resource
    private IAwardRepository awardRepository;


    protected void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        awardRepository.updateUserAwardState(uId, orderId, awardId, grantState);
    }

}
