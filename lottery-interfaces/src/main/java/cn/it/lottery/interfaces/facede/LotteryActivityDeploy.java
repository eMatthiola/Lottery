package cn.it.lottery.interfaces.facede;

import cn.it.lottery.application.process.deploy.IActivityDeployProcess;
import cn.it.lottery.common.Result;
import cn.it.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import cn.it.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import cn.it.lottery.domain.activity.model.vo.ActivityVO;
import cn.it.lottery.interfaces.assembler.IMapping;
import cn.it.lottery.rpc.activity.deploy.ILotteryActivityDeploy;
import cn.it.lottery.rpc.activity.deploy.dto.ActivityDTO;
import cn.it.lottery.rpc.activity.deploy.req.ActivityPageReq;
import cn.it.lottery.rpc.activity.deploy.res.ActivityRes;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName LotteryActivityDeploy
 * @Description 抽奖活动部署
 * @Author Matthiola
 * @Date 2023/11/7 15:16
 */
@Service
public class LotteryActivityDeploy implements ILotteryActivityDeploy {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityBooth.class);

    @Resource
    private IActivityDeployProcess activityDeploy;


    @Resource
    private IMapping<ActivityVO, ActivityDTO> activityMapping;

    @Override
    public ActivityRes queryActivityListByPageForErp(ActivityPageReq req) {

        try {
            logger.info("活动部署分页数据查询开始 erpID：{}", req.getErpId());

            // 1. 包装入参
            ActivityInfoLimitPageReq activityInfoLimitPageReq = new ActivityInfoLimitPageReq(req.getPage(), req.getRows());
            activityInfoLimitPageReq.setActivityId(req.getActivityId());
            activityInfoLimitPageReq.setActivityName(req.getActivityName());

            // 2. 查询结果 implements ILotteryActivityDeploy in the rpc layer and draw the application layer's activityDeploy.queryActivityInfoLimitPage
            ActivityInfoLimitPageRich activityInfoLimitPageRich = activityDeploy.queryActivityInfoLimitPage(activityInfoLimitPageReq);
            List<ActivityVO> activityVOList = activityInfoLimitPageRich.getActivityVOList();
            Long count = activityInfoLimitPageRich.getCount();

            // 3. 转换对象
            List<ActivityDTO> activityDTOList = activityMapping.sourceToTarget(activityVOList);

            // 4. 封装数据
            ActivityRes activityRes = new ActivityRes(Result.buildSuccessResult());
            activityRes.setCount(count);
            activityRes.setActivityDTOList(activityDTOList);

            logger.info("活动部署分页数据查询完成 erpID：{} count：{}", req.getErpId(), count);

            // 5. 返回结果
            return activityRes;
        } catch (Exception e) {
            logger.error("活动部署分页数据查询失败 erpID：{} reqStr：{}", req.getErpId(), JSON.toJSON(req), e);
            return new ActivityRes(Result.buildErrorResult());
        }

    }
}
