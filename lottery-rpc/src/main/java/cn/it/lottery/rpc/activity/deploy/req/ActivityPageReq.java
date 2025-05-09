package cn.it.lottery.rpc.activity.deploy.req;

import cn.it.lottery.common.PageRequest;

import java.io.Serializable;

/**
 * @ClassName ActivityPageReq
 * @Description 分页查询活动
 * @Author Matthiola
 * @Date 2023/11/7 14:34
 */
public class ActivityPageReq extends PageRequest implements Serializable {

    /**
     * ERP ID，记录谁在操作
     */
    private String erpId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    public ActivityPageReq(int page, int rows) {
        super(page, rows);
    }

    public ActivityPageReq(String page, String rows) {
        super(page, rows);
    }

    public String getErpId() {
        return erpId;
    }

    public void setErpId(String erpId) {
        this.erpId = erpId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
