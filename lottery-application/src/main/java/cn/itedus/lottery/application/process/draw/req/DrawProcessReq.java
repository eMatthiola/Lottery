package cn.itedus.lottery.application.process.draw.req;

/**
 * @ClassName DrawProcessReq
 * @Description 抽奖请求
 * @Author Matthiola
 * @Date 2023/10/8 13:40
 */
public class DrawProcessReq {

    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;

    public DrawProcessReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public DrawProcessReq() {

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
