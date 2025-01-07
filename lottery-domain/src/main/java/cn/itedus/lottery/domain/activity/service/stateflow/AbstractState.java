package cn.itedus.lottery.domain.activity.service.stateflow;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.activity.respository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @ClassName AbstractState
 * @Description event 的接口
 * @Author Matthiola
 * @Date 2023/9/19 13:00
 */
public abstract class AbstractState {

    @Resource
    protected IActivityRepository activityRepository;

    /** 提审
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result arraignment(Long activityId, Enum<Constants.ActivityState> currentState);

    /** 审核通过
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result checkPass(Long activityId, Enum<Constants.ActivityState> currentState);

    /** 审核拒绝
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentState);

    /** 撤审撤销
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentState);

    /** 活动关闭
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result close(Long activityId, Enum<Constants.ActivityState> currentState);

    /** 活动开启
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result open(Long activityId, Enum<Constants.ActivityState> currentState);

    /**活动执行
     * @param activityId
     * @param currentState
     * @return
     */
    public abstract Result doing(Long activityId, Enum<Constants.ActivityState> currentState);


}
