package cn.itedus.lottery.application.process.draw;

import cn.itedus.lottery.application.process.draw.req.DrawProcessReq;
import cn.itedus.lottery.application.process.draw.res.DrawProcessResult;
import cn.itedus.lottery.application.process.draw.res.RuleQuantificationCrowdResult;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;

/**
 * @ClassName IActivityProcess
 * @Description 活动流程接口
 * @Author Matthiola
 * @Date 2023/10/8 15:30
 */
public interface IActivityDrawProcess {

    /**
     * 执行抽奖流程
     * @param req 入参
     * @return    抽奖结果
     */
    DrawProcessResult doDrawProcess(DrawProcessReq req);

    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req   规则请求
     * @return      量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdResult doRuleQualificationCrowd(DecisionMatterReq req);
}
