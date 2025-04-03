package cn.it.lottery.domain.activity.service.stateflow.event;

import cn.it.lottery.common.Constants;
import cn.it.lottery.common.Result;
import cn.it.lottery.domain.activity.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

/**
 * @ClassName CloseState
 * @Description 关闭流转
 * @Author Matthiola
 * @Date 2023/9/19 15:05
 */
@Component
public class CloseState extends AbstractState {

    /*can not go arraignment*/
    @Override
    public Result arraignment(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可提审");
    }

    /*can not go checkPass*/
    @Override
    public Result checkPass(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可审核通过");
    }

    /*can not go CheckRefuse*/
    @Override
    public Result checkRefuse(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可审核拒绝");
    }

    /*can not go checkRevoke*/
    @Override
    public Result checkRevoke(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可审核撤销");
    }

    /*can not go close*/
    @Override
    public Result close(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可关闭");
    }

    /*can go open*/
    @Override
    public Result open(Long activityId, Enum<Constants.ActivityState> currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId,currentState,Constants.ActivityState.OPEN);
        return isSuccess ? Result.buildResult(Constants.ResponseCode.SUCCESS, "活动开启成功") : Result.buildErrorResult("活动状态变更失败");
    }

    /*can not go doing*/
    @Override
    public Result doing(Long activityId, Enum<Constants.ActivityState> currentState) {
        return Result.buildResult(Constants.ResponseCode.UN_ERROR, "活动关闭不可进行中");
    }
}
