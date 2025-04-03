package cn.it.lottery.infrastructure.dao;

import cn.it.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName RuleTreeNodeDao
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:01
 */
@Mapper
public interface RuleTreeNodeDao {
    /*
     * @Description //TODO 
     * @Date    
    **/
    List<RuleTreeNode> quaryRuleTreeNodeList(Long treeId);
}
