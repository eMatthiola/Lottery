package cn.it.lottery.domain.activity.respository;

import cn.it.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import cn.it.lottery.domain.activity.model.vo.DrawOrderVO;
import cn.it.lottery.domain.activity.model.vo.InvoiceVO;
import cn.it.lottery.domain.activity.model.vo.UserTakeActivityVO;

import java.util.Date;
import java.util.List;

/**
 * @ClassName IUserTakeActivityRepository
 * @Description 用户参与活动仓储接口
 * @Author Matthiola
 * @Date 2023/10/1 11:14
 */
public interface IUserTakeActivityRepository {

    /**
     * 查询用户参与活动次数
     * @param activityId 活动ID
     * @param activityName 活动名称
     * @param takeCount 活动参与次数
     * @param userTakeLeftCount 用户剩余参与次数
     * @param uId 用户ID
     * @param partakeDate 参与时间
     * @return
     */
    int subtractionLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId, Date partakeDate);


    /**
     * 领取活动
     * @param activityId 活动ID
     * @param activityName 活动名称
     * @param takeCount 活动参与次数
     * @param userTakeLeftCount 用户剩余参与次数
     * @param uId 用户ID
     * @param takeDate 参与时间
     * @param takeId 参与ID
     */
    void takeActivity(Long activityId, String activityName, Long strategyId, Integer takeCount, Integer userTakeLeftCount, String uId, Date takeDate, Long takeId);


    /**
     * 锁定活动领取记录
     * @param uId 用户ID
     * @param activityId 活动ID
     * @param takeId 领取ID
     * @return
     */
    int lockTackActivity(String uId, Long activityId, Long takeId);

    /**
     * 保存抽奖信息
     * @param drawOrder 中奖单
     */
    void saveUserStrategyExport(DrawOrderVO drawOrder);

    /**
     * 查询用户未消费的活动参与单
     * @param activityId 活动ID
     * @param uId 用户ID
     * @return
     */
    UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 更新发货单MQ状态
     *
     * @param uId     用户ID
     * @param orderId 订单ID
     * @param mqState MQ 发送状态
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);


    /**
     * 扫描发货单 MQ 状态，把未发送 MQ 的单子扫描出来，做补偿
     *
     * @return 发货单
     */
    List<InvoiceVO> scanInvoiceMqState();


    /**
     * 更新活动库存
     *
     * @param activityPartakeRecordVO   活动领取记录
     */
    void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO);
}
