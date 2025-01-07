package cn.itedus.lottery.domain.rule.service.engine.impl;

import cn.itedus.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.res.EngineResult;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.itedus.lottery.domain.rule.repository.IRuleRepository;
import cn.itedus.lottery.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName RuleEngineHandle
 * @Description
 * @Author Matthiola
 * @Date 2023/10/20 12:58
 */
@Service("ruleEngineHandle")
public class RuleEngineHandle extends EngineBase {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatterReq req) {
        //1.get rich including root  node  line
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(req.getTreeId());
        if (treeRuleRich == null){
            throw new RuntimeException("Tree Rich is null");
        }

        //2.get the node from rich and find out if the node is a fruit or not
        //if node is fruit get it
        //if not , go to iterate from line until reach the fruit
        TreeNodeVO treeNodeVO = engineDecisionMaker(treeRuleRich, req);


        //3.return the fruit
        return new EngineResult(req.getUserId(), treeNodeVO.getTreeId(), treeNodeVO.getTreeNodeId(), treeNodeVO.getNodeValue());
    }
}
