package cn.itedus.lottery.domain.award.repository;

/**
 * Created by IntelliJ IDEA.
 * @description: 奖品表仓储服务接口
 * @Author : 38309
 * @create 2023/9/12 14:20
 */
public interface IAwardRepository {


    /**
     * 更新user_strategy_export中的奖品发放状态
     *
     * @param uId               用户ID
     * @param orderId           订单ID
     * @param awardId           奖品ID
     * @param grantState        奖品状态
     */
    void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState);


}
