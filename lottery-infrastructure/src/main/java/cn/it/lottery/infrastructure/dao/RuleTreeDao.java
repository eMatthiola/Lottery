package cn.it.lottery.infrastructure.dao;

import cn.it.lottery.infrastructure.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName RuleTreeDao
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 15:00
 */
@Mapper
public interface RuleTreeDao {

    RuleTree queryTreeByTreeId(Long treeId);
}
