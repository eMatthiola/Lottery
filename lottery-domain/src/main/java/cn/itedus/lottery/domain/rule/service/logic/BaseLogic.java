package cn.itedus.lottery.domain.rule.service.logic;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * @ClassName BaseLogic
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 21:36
 */
public abstract class BaseLogic implements LogicFilter{
    @Override
    public abstract String matterValue(DecisionMatterReq decisionMatter) ;

    public Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList){
        for (TreeNodeLineVO treeNodeLineVO: treeNodeLineInfoList) {
            if(decisionLogic(treeNodeLineVO, matterValue)){
                return treeNodeLineVO.getNodeIdTo();
            }
        }
        return Constants.Global.TREE_NULL_NODE;
    }

    private boolean decisionLogic(TreeNodeLineVO treeNodeLineVO, String matterValue){
        switch (treeNodeLineVO.getRuleLimitType()){
            case Constants.RuleLimitType.EQUAL:
                return matterValue.equals(treeNodeLineVO.getRuleLimitValue()); // man == man
            case Constants.RuleLimitType.GT:
                return Double.parseDouble(matterValue) > Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(treeNodeLineVO.getRuleLimitValue()); //20<25
            case Constants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >=Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.LE:
                return Double.parseDouble(matterValue) <=Double.parseDouble(treeNodeLineVO.getRuleLimitValue());
            default:
                return false;
        }
    }


}
