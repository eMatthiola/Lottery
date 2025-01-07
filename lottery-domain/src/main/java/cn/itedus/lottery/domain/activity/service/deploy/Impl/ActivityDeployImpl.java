package cn.itedus.lottery.domain.activity.service.deploy.Impl;

import cn.itedus.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import cn.itedus.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import cn.itedus.lottery.domain.activity.model.req.ActivityConfigReq;
import cn.itedus.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import cn.itedus.lottery.domain.activity.model.vo.ActivityVO;
import cn.itedus.lottery.domain.activity.model.vo.AwardVO;
import cn.itedus.lottery.domain.activity.model.vo.StrategyDetailVO;
import cn.itedus.lottery.domain.activity.model.vo.StrategyVO;
import cn.itedus.lottery.domain.activity.respository.IActivityRepository;
import cn.itedus.lottery.domain.activity.service.deploy.IActivityDeploy;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CreateActivityImpl
 * @Description 活动创建具体实现
 * @Author Matthiola
 * @Date 2023/9/18 11:02
 */
@Service
public class ActivityDeployImpl implements IActivityDeploy {
    /*日志记录*/
    private static final Logger logger= LoggerFactory.getLogger(ActivityDeployImpl.class);

    /*VO转PO,拿到活动仓储层接口*/
    @Resource
    private IActivityRepository activityRepository;

    /**获取活动信息
     *
     * @param req
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createActivity(ActivityConfigReq req) {
                /*开启日志记录*/
        logger.info("创建活动配置开始，activityId：{}", req.getActivityId());
        ActivityConfigRich activityConfigRich =req.getActivityConfigRich();

        try {
            /*获取活动信息*/
            ActivityVO activity =activityConfigRich.getActivityVO();
            /* VO转PO*/
            activityRepository.addActivity(activity);
            /* 获取奖品信息*/
            List<AwardVO> awardList =activityConfigRich.getAwardList();
            activityRepository.addAward(awardList);
            /* 获取策略信息*/
            StrategyVO strategy =activityConfigRich.getStrategyVO();
            activityRepository.addStrategy(strategy);
            /* 获取策略细节信息*/
            List<StrategyDetailVO> strategyDetailList =activityConfigRich.getStrategyVO().getStrategyDetailList();
            activityRepository.addStrategyDetail(strategyDetailList);
            logger.info("创建活动配置完成，activityId：{}", req.getActivityId());
        }
        catch (DuplicateKeyException e) {
            logger.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", req.getActivityId(), JSON.toJSONString(req), e);
            throw e;
        }




    }

    @Override
    public void updateActivity(ActivityConfigReq req) {
        //todo  update activity
    }

    @Override
    public List<ActivityVO> scanToDoActivityList(Long id) {

        return activityRepository.scanToDoActivityList(id);
    }

    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {
        return activityRepository.queryActivityInfoLimitPage(req);
    }
}
