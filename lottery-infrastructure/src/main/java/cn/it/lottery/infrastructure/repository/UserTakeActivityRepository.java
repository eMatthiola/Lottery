package cn.it.lottery.infrastructure.repository;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import cn.it.lottery.domain.activity.model.vo.DrawOrderVO;
import cn.it.lottery.domain.activity.model.vo.InvoiceVO;
import cn.it.lottery.domain.activity.model.vo.UserTakeActivityVO;
import cn.it.lottery.domain.activity.respository.IUserTakeActivityRepository;
import cn.it.lottery.infrastructure.dao.IActivityDao;
import cn.it.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.it.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import cn.it.lottery.infrastructure.dao.IUserTakeActivityDao;
import cn.it.lottery.infrastructure.po.Activity;
import cn.it.lottery.infrastructure.po.UserStrategyExport;
import cn.it.lottery.infrastructure.po.UserTakeActivity;
import cn.it.lottery.infrastructure.po.UserTakeActivityCount;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserTakeActivityRepository
 * @Description 用户参与活动仓储
 * @Author Matthiola
 * @Date 2023/10/1 11:13
 */
@Component
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;

    @Resource
    private IUserTakeActivityDao userTakeActivityDao;

    @Resource
     private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public int subtractionLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId, Date partakeDate) {
        if(null == userTakeLeftCount){
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            userTakeActivityCount.setTotalCount(takeCount);
            userTakeActivityCount.setLeftCount(takeCount - 1);
            userTakeActivityCountDao.insert(userTakeActivityCount);
            return 1;

        }else {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setuId(uId);
            userTakeActivityCount.setActivityId(activityId);
            return userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        }
    }

    @Override
    public void takeActivity(Long activityId, String activityName, Long strategyId, Integer takeCount, Integer userTakeLeftCount, String uId, Date takeDate, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setActivityName(activityName);
        userTakeActivity.setTakeDate(takeDate);
        if(null == userTakeLeftCount){
            userTakeActivity.setTakeCount(1);
        }else {
            userTakeActivity.setTakeCount(takeCount - userTakeLeftCount);
        }
        userTakeActivity.setStrategyId(strategyId);
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());
        String uuid = uId + "_" + activityId + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);

        userTakeActivityDao.insert(userTakeActivity);
    }

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        //USE userTakeActivityDao information to determine UserTakeActivityVO
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setuId(uId);
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityDao.queryNoConsumedTakeActivityOrder(userTakeActivity);

        // 未查询到符合的领取单，直接返回 NULL
        if (null==noConsumedTakeActivityOrder) {
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        userTakeActivityVO.setActivityId(noConsumedTakeActivityOrder.getActivityId());
        userTakeActivityVO.setTakeId(noConsumedTakeActivityOrder.getTakeId());
        userTakeActivityVO.setStrategyId(noConsumedTakeActivityOrder.getStrategyId());
        userTakeActivityVO.setState(noConsumedTakeActivityOrder.getState());

        return userTakeActivityVO;
    }

    @Override
    public int lockTackActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setuId(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        //todo >> and state = 0
        return userTakeActivityDao.lockTackActivity(userTakeActivity);
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(drawOrder.getuId());
        userStrategyExport.setActivityId(drawOrder.getActivityId());
        userStrategyExport.setOrderId(drawOrder.getOrderId());
        userStrategyExport.setStrategyId(drawOrder.getStrategyId());
        userStrategyExport.setStrategyMode(drawOrder.getStrategyMode());
        userStrategyExport.setGrantType(drawOrder.getGrantType());
        userStrategyExport.setGrantDate(drawOrder.getGrantDate());
        userStrategyExport.setGrantState(drawOrder.getGrantState());
        userStrategyExport.setAwardId(drawOrder.getAwardId());
        userStrategyExport.setAwardType(drawOrder.getAwardType());
        userStrategyExport.setAwardName(drawOrder.getAwardName());
        userStrategyExport.setAwardContent(drawOrder.getAwardContent());
        userStrategyExport.setUuid(String.valueOf(drawOrder.getOrderId()));
        userStrategyExport.setMqState(Constants.MQState.INIT.getCode());

        userStrategyExportDao.insert(userStrategyExport);

    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqState(mqState);
        userStrategyExportDao.updateInvoiceMqState(userStrategyExport);

    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState() {

        // 查询发送MQ失败和超时30分钟，未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportDao.scanInvoiceMqState();

        // 转换对象
        ArrayList<InvoiceVO> invoiceVOList = new ArrayList<>(userStrategyExportList.size());
        for (UserStrategyExport userStrategyExport : userStrategyExportList) {
            InvoiceVO invoiceVO = new InvoiceVO();
            invoiceVO.setuId(userStrategyExport.getuId());
            invoiceVO.setOrderId(userStrategyExport.getOrderId());
            invoiceVO.setAwardId(userStrategyExport.getAwardId());
            invoiceVO.setAwardType(userStrategyExport.getAwardType());
            invoiceVO.setAwardName(userStrategyExport.getAwardName());
            invoiceVO.setAwardContent(userStrategyExport.getAwardContent());
            invoiceVOList.add(invoiceVO);

        }
        return invoiceVOList;
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        activityDao.updateActivityStock(activity);

    }
}
