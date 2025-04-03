package cn.it.lottery.infrastructure.po;


import java.util.Date;

public class RuleTree {


  private Long id;
  private String treeName;
  private String treeDesc;

  /** 规则树根ID */
  private Long treeRootNodeId;
  private Date createTime;
  private Date updateTime;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getTreeName() {
    return treeName;
  }

  public void setTreeName(String treeName) {
    this.treeName = treeName;
  }


  public String getTreeDesc() {
    return treeDesc;
  }

  public void setTreeDesc(String treeDesc) {
    this.treeDesc = treeDesc;
  }


  public Long getTreeRootNodeId() {
    return treeRootNodeId;
  }

  public void setTreeRootNodeId(Long treeRootNodeId) {
    this.treeRootNodeId = treeRootNodeId;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
