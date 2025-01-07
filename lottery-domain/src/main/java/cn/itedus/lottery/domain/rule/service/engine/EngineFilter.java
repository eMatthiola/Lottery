package cn.itedus.lottery.domain.rule.service.engine;

import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.res.EngineResult;

/**
 * @ClassName EngineFilter
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 21:13
 */
public interface EngineFilter {

 EngineResult process(final DecisionMatterReq req);
}
