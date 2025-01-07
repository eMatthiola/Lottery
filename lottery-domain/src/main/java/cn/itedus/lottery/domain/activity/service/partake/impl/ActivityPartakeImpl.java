package cn.itedus.lottery.domain.activity.service.partake.impl;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.domain.activity.model.req.PartakeReq;
import cn.itedus.lottery.domain.activity.model.res.StockResult;
import cn.itedus.lottery.domain.activity.model.vo.*;
import cn.itedus.lottery.domain.activity.respository.IUserTakeActivityRepository;
import cn.itedus.lottery.domain.activity.service.partake.BaseActivityPartake;
import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import middleware.db.router.strategy.IDBRouterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ActivityPartakeImpl
 * @Description 活动参与功能实现
 * @Author Matthiola
 * @Date 2023/9/30 15:58
 */
@Service
public class ActivityPartakeImpl extends BaseActivityPartake {
    private Logger logger = LoggerFactory.getLogger(ActivityPartakeImpl.class);

    @Resource
    private IUserTakeActivityRepository userTakeActivityRepository;

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;



    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Override
    protected Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
        // 校验：活动状态
        if (!Constants.ActivityState.DOING.getCode().equals(bill.getState())){
            logger.warn("Current activity state is not available state：{}", bill.getState());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "Current activity state is not available");
        }

        // 校验：活动日期
        if(bill.getBeginDateTime().after(partake.getPartakeDate() ) || bill.getEndDateTime().before(partake.getPartakeDate())){
            logger.warn("Activity time range is not available beginDateTime：{} endDateTime：{}", bill.getBeginDateTime(), bill.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "Activity time range is not available");
        }

        // 校验：活动库存
        if (bill.getStockSurplusCount() <= 0){
            logger.warn("Activity remaining inventory is not available stockSurplusCount：{}", bill.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "Activity remaining inventory is not available");
        }

        // 校验：个人库存 - 个人活动剩余可领取次数
        if (bill.getUserTakeLeftCount() <= 0){
            logger.warn("Personal remaining inventory is not available personalStockSurplusCount：{}", bill.getUserTakeLeftCount());
            return Result.buildResult(Constants.ResponseCode.UN_ERROR, "Personal remaining inventory is not available");
        }
        return Result.buildSuccessResult();
    }

    @Override
    protected Result subtractionActivityStock(PartakeReq req) {
        int count =activityRepository.subtractionActivityStock(req.getActivityId());
        if(0==count){
            logger.error("Deduction activity inventory failed activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    protected Result grabActivity(PartakeReq req, ActivityBillVO activityBillVO,Long takeId) {

        try {
            dbRouter.doRouter(req.getuId());
            return transactionTemplate.execute(transactionStatus -> {
                try {
                    // 扣减个人已参与次数 from user_take_activity_count
                    int updateCount = userTakeActivityRepository.subtractionLeftCount(
                            activityBillVO.getActivityId(), activityBillVO.getActivityName(),
                            activityBillVO.getTakeCount(), activityBillVO.getUserTakeLeftCount(),
                            req.getuId(), req.getPartakeDate());
                    if (0 == updateCount){
                        transactionStatus.setRollbackOnly();
                        logger.error("Deduction personal participation times failed activityId：{} uId：{}", req.getActivityId(), req.getuId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 插入领取活动信息 to user_take_activity
                    userTakeActivityRepository.takeActivity(
                            activityBillVO.getActivityId(), activityBillVO.getActivityName(),
                            activityBillVO.getStrategyId(), activityBillVO.getTakeCount(),
                            activityBillVO.getUserTakeLeftCount(),
                            req.getuId(), req.getPartakeDate(), takeId);
                }
                catch (DuplicateKeyException e){
                    transactionStatus.setRollbackOnly();
                    logger.error("领取活动，唯一索引冲突 activityId：{} uId：{}", req.getActivityId(), req.getuId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUP);
                }
                return Result.buildSuccessResult();
            });
        }
        finally {
            dbRouter.clear();
        }


    }

    @Override
    protected UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        return userTakeActivityRepository.queryNoConsumedTakeActivityOrder(activityId, uId);
    }

    @Override
    protected StockResult subtractionActivityStockByRedis(String uId, Long activityId, Integer stockCount) {
        return activityRepository.subtractionActivityStockByRedis(uId, activityId, stockCount);
    }

    @Override
    protected void recoverActivityCacheStockByRedis(Long activityId, String tokenKey, String code) {
        activityRepository.recoverActivityCacheStockByRedis(activityId, tokenKey, code);
    }

    @Override
    public Result recordDrawOrder(DrawOrderVO drawOrder) {
        try {
            dbRouter.doRouter(drawOrder.getuId());
            return transactionTemplate.execute(transactionStatus -> {
                try {
                    // 锁定活动领取记录
                    int lockCount = userTakeActivityRepository.lockTackActivity(drawOrder.getuId(), drawOrder.getActivityId(), drawOrder.getTakeId());
                    if (0 == lockCount) {
                        transactionStatus.setRollbackOnly();
                        logger.error("记录中奖单，个人参与活动抽奖已消耗完 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getuId());
                        return Result.buildErrorResult(Constants.ResponseCode.NO_UPDATE.getInfo());
                    }
                    // 保存抽奖信息 to user_strategy_export
                    userTakeActivityRepository.saveUserStrategyExport(drawOrder);
                } catch (DuplicateKeyException e) {
                    transactionStatus.setRollbackOnly();
                    logger.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrder.getActivityId(), drawOrder.getuId(), e);
                    return Result.buildErrorResult(Constants.ResponseCode.INDEX_DUP.getInfo());
                }
                return Result.buildSuccessResult();
            });

        } finally {
            dbRouter.clear();
        }

    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        userTakeActivityRepository.updateInvoiceMqState(uId, orderId, mqState);

    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState(int dbCount, int tbCount) {

        try{
            // 设置路由
            dbRouter.setDBKey(dbCount);
            dbRouter.setTBKey(tbCount);

            // 查询数据
            List<InvoiceVO> invoiceVOList = userTakeActivityRepository.scanInvoiceMqState();
            return invoiceVOList;

        }finally {
            dbRouter.clear();

        }

    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        userTakeActivityRepository.updateActivityStock(activityPartakeRecordVO);
    }
}
