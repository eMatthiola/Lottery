package cn.it.lottery.domain.activity.service.partake;

import cn.it.lottery.domain.activity.model.req.PartakeReq;
import cn.it.lottery.domain.activity.model.vo.ActivityBillVO;
import cn.it.lottery.domain.activity.respository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @ClassName ActivityPartakeSupport
 * @Description 活动领取模操作，一些通用的数据服务
 * @Author Matthiola
 * @Date 2023/9/30 14:56
 */
public class ActivityPartakeSupport {


    @Resource
    protected IActivityRepository activityRepository;

    //query activityBillVO
    protected ActivityBillVO queryActivityBillVO(PartakeReq req) {
        return activityRepository.queryActivityBill(req);
    }
}
