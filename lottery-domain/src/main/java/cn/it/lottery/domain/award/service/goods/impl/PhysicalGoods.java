package cn.it.lottery.domain.award.service.goods.impl;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.award.model.req.GoodsReq;
import cn.it.lottery.domain.award.model.res.DistributionRes;
import cn.it.lottery.domain.award.service.goods.DistributionBase;
import cn.it.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * @description: 实物发货类商品
 * @Author : 38309
 * @create 2023/9/12 11:13
 */
@Component
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用优惠券发放接口
        logger.info("模拟调用优惠券发放接口 uId: {} awardContent: {}",req.getuId(),req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());

        //恭喜你中奖PhysicalGoods啦
        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(),Constants.AwardState.SUCCESS.getInfo());

    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.CouponGoods.getCode();
    }
}
