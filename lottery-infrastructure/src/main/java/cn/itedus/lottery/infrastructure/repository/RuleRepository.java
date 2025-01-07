package cn.itedus.lottery.infrastructure.repository;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeLineVO;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.itedus.lottery.domain.rule.model.vo.TreeRootVO;
import cn.itedus.lottery.domain.rule.repository.IRuleRepository;
import cn.itedus.lottery.infrastructure.dao.RuleTreeDao;
import cn.itedus.lottery.infrastructure.dao.RuleTreeNodeDao;
import cn.itedus.lottery.infrastructure.dao.RuleTreeNodeLineDao;
import cn.itedus.lottery.infrastructure.po.RuleTree;
import cn.itedus.lottery.infrastructure.po.RuleTreeNode;
import cn.itedus.lottery.infrastructure.po.RuleTreeNodeLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RuleRepository
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:40
 */

@Repository
public class RuleRepository implements IRuleRepository {

    @Resource
    private RuleTreeDao ruleTreeDao;
    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;


    /**
     * @Description  get everything from three tables
     * @param treeId    决策树ID
     * @return
     */
    @Override
    public TreeRuleRich queryTreeRuleRich(Long treeId) {

        //to get the richTree that I need the three filed.
        // The first is the TreeRootVO treeRoot,
        RuleTree ruleTree = ruleTreeDao.queryTreeByTreeId(treeId);
        TreeRootVO treeRootVO = new TreeRootVO();
        treeRootVO.setTreeId(ruleTree.getId());
        treeRootVO.setTreeName(ruleTree.getTreeName());
        treeRootVO.setTreeRootNodeId(ruleTree.getTreeRootNodeId());

       // second is the Map<Long, TreeNodeVO> treeNodeMap;
        Map<Long, TreeNodeVO> treeNodeVOMap  = new HashMap<>();
        List<RuleTreeNode> ruleTreeNodeList = ruleTreeNodeDao.quaryRuleTreeNodeList(treeId);
        for (RuleTreeNode treeNode : ruleTreeNodeList) {
            List<TreeNodeLineVO> treeNodeLineVOList = new ArrayList<>();
            //stem continue to iterate
            if (Constants.NodeType.STEM.equals(treeNode.getNodeType())){

                //third one is treeNodeLineVOList
                RuleTreeNodeLine ruleTreeNodeLine = new RuleTreeNodeLine();
                ruleTreeNodeLine.setTreeId(treeId);
                ruleTreeNodeLine.setNodeIdFrom(treeNode.getId());
                List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.queryRuleTreeNodeLineList(ruleTreeNodeLine);

                for (RuleTreeNodeLine nodeLine : ruleTreeNodeLineList){
                    TreeNodeLineVO treeNodeLineVO = new TreeNodeLineVO();
                    treeNodeLineVO.setNodeIdFrom(nodeLine.getNodeIdFrom());
                    treeNodeLineVO.setNodeIdTo(nodeLine.getNodeIdTo());
                    treeNodeLineVO.setRuleLimitType(nodeLine.getRuleLimitType());
                    treeNodeLineVO.setRuleLimitValue(nodeLine.getRuleLimitValue());
                    //add to array
                    treeNodeLineVOList.add(treeNodeLineVO);
                }

            }
            //fruit is going to save
            TreeNodeVO treeNodeVO = new TreeNodeVO();
            treeNodeVO.setTreeId(treeNode.getTreeId());
            treeNodeVO.setTreeNodeId(treeNode.getId());
            treeNodeVO.setNodeType(treeNode.getNodeType());
            treeNodeVO.setNodeValue(treeNode.getNodeValue());
            treeNodeVO.setRuleKey(treeNode.getRuleKey());
            treeNodeVO.setRuleDesc(treeNode.getRuleDesc());

            treeNodeVO.setTreeNodeLineInfoList(treeNodeLineVOList);

            //put every fruit into map
            treeNodeVOMap.put(treeNode.getId(), treeNodeVO);
        }


        TreeRuleRich treeRuleRich = new TreeRuleRich();
        treeRuleRich.setTreeRoot(treeRootVO);
        treeRuleRich.setTreeNodeMap(treeNodeVOMap);
        return treeRuleRich;
    }
}
