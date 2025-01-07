package cn.itedus.lottery.domain.rule.service.engine;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.res.EngineResult;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.itedus.lottery.domain.rule.model.vo.TreeRootVO;
import cn.itedus.lottery.domain.rule.service.logic.LogicFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName EngineBase
 * @Description Common move
 * @Author Matthiola
 * @Date 2023/10/19 21:14
 */
public abstract class EngineBase extends EngineConfig implements EngineFilter {

    private Logger logger = LoggerFactory.getLogger(EngineBase.class);
    @Override
    public EngineResult process(DecisionMatterReq req) {
        throw new RuntimeException("未实现规则引擎服务");
    }

    protected TreeNodeVO engineDecisionMaker(TreeRuleRich treeRuleRich, DecisionMatterReq matterReq){

        TreeRootVO treeRoot = treeRuleRich.getTreeRoot();
        Long treeRootNodeId = treeRoot.getTreeRootNodeId(); //1

        Map<Long, TreeNodeVO> treeNodeMap = treeRuleRich.getTreeNodeMap();
        TreeNodeVO treeNodeVOMap = treeNodeMap.get(treeRootNodeId);// 由根节点ID拿到Node

        // 节点类型[NodeType]；1子叶、2果实  寻找果实节点
        while (Constants.NodeType.STEM.equals(treeNodeVOMap.getNodeType())){
            String ruleKey = treeNodeVOMap.getRuleKey(); //性别 OR 年龄
            LogicFilter logicFilter = logicFilterMap.get(ruleKey);
            String matterValue = logicFilter.matterValue(matterReq); //to String

            Long nextNode = logicFilter.filter(matterValue, treeNodeVOMap.getTreeNodeLineInfoList()); //age or gender first then rule_limit_value(25) compare with matterValue(20)
            treeNodeVOMap = treeNodeMap.get(nextNode); // 拿到下一个节点
//            logger.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}",
//                    treeRoot.getTreeName(), matterReq.getUserId(), matterReq.getTreeId(), treeNodeVOMap.getTreeNodeId(), ruleKey, matterValue);

        }
        return treeNodeVOMap;

    }
}
