package cn.it.lottery.infrastructure.dao;

import cn.it.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface IStrategyDetailDao {

    //数组List<StrategyDetail> 一个strategyId 可能对应多个奖品 1vn
//    StrategyDetail queryStrategyDetailById(Long strategyId);
    /**
     * 查询策略表详细配置
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<StrategyDetail> queryStrategyDetailList(Long strategyId);

    /**
     * 查询无库存策略奖品ID
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<String> queryNoStockStrategyAwardList(Long strategyId);

    /**
     * 扣减库存
     * @param strategyDetailReq 策略ID、奖品ID
     * @return                  返回结果
     */
    int deductStock(StrategyDetail strategyDetailReq);

    void insertList(List<StrategyDetail> req);

}
