package cn.it.lottery.infrastructure.repository;

import cn.it.lottery.domain.award.repository.IAwardRepository;
import cn.it.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.it.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @description: 奖品表仓储服务
 * @Author : 38309
 * @create 2023/9/12 14:41
 */
@Component
public class AwardRepository implements IAwardRepository {

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {

        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }
}
