package cn.it.lottery.domain.rule.repository;

import cn.it.lottery.domain.rule.model.aggregates.TreeRuleRich;

/**
 * @ClassName IRuleRepository
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:38
 */
public interface IRuleRepository {

    /**
     * get the rich object
     *
     * @param treeId    决策树ID
     * @return          决策树配置
     */
   TreeRuleRich queryTreeRuleRich(Long treeId);
}
