package cn.it.lottery.rpc.activity.deploy;

import cn.it.lottery.rpc.activity.deploy.req.ActivityPageReq;
import cn.it.lottery.rpc.activity.deploy.res.ActivityRes;

/**
 * @ClassName ILotteryActivityDeploy
 * @Description 抽奖活动部署服务接口
 * @Author Matthiola
 * @Date 2023/11/7 14:33
 */
public interface ILotteryActivityDeploy {

    /**
     * 通过分页查询活动列表信息，给ERP运营使用
     *
     * @param req   查询参数
     * @return      查询结果
     */
    ActivityRes queryActivityListByPageForErp(ActivityPageReq req);
}
