package cn.it.lottery.application.process.deploy.impl;

import cn.it.lottery.application.process.deploy.IActivityDeployProcess;
import cn.it.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import cn.it.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import cn.it.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName ActivityDeployProcessImpl
 * @Description 活动部署实现
 * @Author Matthiola
 * @Date 2023/11/7 15:13
 */

@Service
public class ActivityDeployProcessImpl implements IActivityDeployProcess {

    @Resource
    private IActivityDeploy activityDeploy;

    //draw domain layer
    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {
        return activityDeploy.queryActivityInfoLimitPage(req);
    }
}
