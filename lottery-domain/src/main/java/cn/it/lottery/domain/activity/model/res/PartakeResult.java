package cn.it.lottery.domain.activity.model.res;

import cn.it.lottery.common.Result;

/**
 * @ClassName PartakeResult
 * @Description 活动参与结果
 * @Author Matthiola
 * @Date 2023/9/30 14:50
 */
public class PartakeResult extends Result {

    /** 策略ID */
    private Long strategyId;
    /** 活动领取ID */
    private Long takeId;
    /** 库存 */
    private Integer stockCount;
    /** activity 库存剩余 */
    private Integer stockSurplusCount;

    public PartakeResult(String code, String info) {
        super(code, info);
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getStockSurplusCount() {
        return stockSurplusCount;
    }

    public void setStockSurplusCount(Integer stockSurplusCount) {
        this.stockSurplusCount = stockSurplusCount;
    }
}
