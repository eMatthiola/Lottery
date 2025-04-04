package cn.it.lottery.application.process.draw.res;

import cn.it.lottery.common.Result;

/**
 * @ClassName RuleQuantificationCrowdResult
 * @Description  the result of rule quantification crowd
 * @Author Matthiola
 * @Date 2023/10/20 16:42
 */
public class RuleQuantificationCrowdResult extends Result {

    /** 活动ID */
    private Long activityId;


    public RuleQuantificationCrowdResult(String code, String info) {
        super(code, info);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
