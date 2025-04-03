package cn.it.lottery.rpc.activity.booth.req;

import java.io.Serializable;

/**
 * @ClassName DrawReq
 * @Description lottery request
 * @Author Matthiola
 * @Date 2023/10/20 17:08
 */
public class DrawReq implements Serializable {


    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

    public DrawReq() {
    }

    public DrawReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
