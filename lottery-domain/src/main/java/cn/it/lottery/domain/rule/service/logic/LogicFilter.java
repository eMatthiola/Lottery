package cn.it.lottery.domain.rule.service.logic;

import cn.it.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.it.lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * @ClassName LogicFilter
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 21:33
 */
public interface LogicFilter {
    /**
     *
     * @param matterValue 决策值
     * @param treeNodeLineInfoList 决策节点
     * @return                    下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList);

    String matterValue(DecisionMatterReq decisionMatter);
}
