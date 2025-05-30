package cn.it.lottery.infrastructure.po;

import java.math.BigDecimal;

/**
 * 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 公众号：bugstack虫洞栈
 *
 * @author 小傅哥，微信：fustack
 * <p>
 * 策略明细
 */
public class StrategyDetail {

  /**
   * 自增ID
   */
  private String id;

  /**
   * 策略ID
   */
  private Long strategyId;

  /**
   * 奖品ID
   */
  private String awardId;

  /**
   * 奖品名称
   */
  private String awardName;

  /**
   * 奖品库存
   */
  private Integer awardCount;

  /**
   * 奖品剩余库存
   */
  private Integer awardSurplusCount;

  /**
   * 中奖概率
   */
  private BigDecimal awardRate;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 修改时间
   */
  private String updateTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getStrategyId() {
    return strategyId;
  }

  public void setStrategyId(Long strategyId) {
    this.strategyId = strategyId;
  }

  public String getAwardId() {
    return awardId;
  }

  public void setAwardId(String awardId) {
    this.awardId = awardId;
  }

  public String getAwardName() {
    return awardName;
  }

  public void setAwardName(String awardName) {
    this.awardName = awardName;
  }

  public Integer getAwardCount() {
    return awardCount;
  }

  public void setAwardCount(Integer awardCount) {
    this.awardCount = awardCount;
  }

  public Integer getAwardSurplusCount() {
    return awardSurplusCount;
  }

  public void setAwardSurplusCount(Integer awardSurplusCount) {
    this.awardSurplusCount = awardSurplusCount;
  }

  public BigDecimal getAwardRate() {
    return awardRate;
  }

  public void setAwardRate(BigDecimal awardRate) {
    this.awardRate = awardRate;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
}
