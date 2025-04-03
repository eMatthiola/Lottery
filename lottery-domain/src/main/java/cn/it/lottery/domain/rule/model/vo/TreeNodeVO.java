package cn.it.lottery.domain.rule.model.vo;

import java.util.List;

/**
 * @ClassName TreeNodeVO
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:07
 */
public class TreeNodeVO {

    /** 规则树ID */
    private Long treeId;

    /** 规则树节点ID */
    private Long treeNodeId;

    /** 节点类型；1子叶、2果实 */
    private Integer nodeType;

    /** 节点值[nodeType=2]；果实值 */
    private String nodeValue;

    /** 规则Key 性别 OR 年龄 */
    private String ruleKey;

    /** 规则描述 */
    private String ruleDesc;

    /** special add
     *  节点链路 */
    private List<TreeNodeLineVO> treeNodeLineInfoList;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(Long treeNodeId) {
        this.treeNodeId = treeNodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public List<TreeNodeLineVO> getTreeNodeLineInfoList() {
        return treeNodeLineInfoList;
    }

    public void setTreeNodeLineInfoList(List<TreeNodeLineVO> treeNodeLineInfoList) {
        this.treeNodeLineInfoList = treeNodeLineInfoList;
    }
}
