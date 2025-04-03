package cn.it.lottery.infrastructure.dao;

import cn.it.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStrategyDao {
    Strategy queryStrategy(Long strategyId);

    void insert(Strategy req);

}
