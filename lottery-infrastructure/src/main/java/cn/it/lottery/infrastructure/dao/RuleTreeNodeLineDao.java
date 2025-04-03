package cn.it.lottery.infrastructure.dao;

import cn.it.lottery.infrastructure.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName RuleTreeNodeLineDao
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:01
 */
@Mapper
public interface RuleTreeNodeLineDao {

    /**
     *
     * @param ruleTreeNodeLine
     * @return
     */
    List<RuleTreeNodeLine> queryRuleTreeNodeLineList(RuleTreeNodeLine ruleTreeNodeLine);


    int queryTreeNodeLineCount(Long treeId);
}
