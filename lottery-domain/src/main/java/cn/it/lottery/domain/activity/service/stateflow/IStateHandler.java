package cn.it.lottery.domain.activity.service.stateflow;

import cn.it.lottery.common.Constants;
import cn.it.lottery.common.Result;

/**
 * @ClassName IStateHandler
 * @Description 对活动状态请求进行流转处理
 * @Author Matthiola
 * @Date 2023/9/19 12:14
 */
public interface IStateHandler {
    /**
     * 提审
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result arraignment(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核通过
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     */
    Result checkPass(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核不通过
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     **/
    Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核撤销
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     * */
    Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核关闭
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     * */
    Result close(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核开启
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     * */
    Result open(Long activityId, Enum<Constants.ActivityState> currentStatus);

    /**
     * 审核中
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return              审核结果
     * */
    Result doing(Long activityId, Enum<Constants.ActivityState> currentStatus);

}
