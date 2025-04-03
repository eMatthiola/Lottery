package cn.it.lottery.domain.award.service.goods.impl;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.award.model.req.GoodsReq;
import cn.it.lottery.domain.award.model.res.DistributionRes;
import cn.it.lottery.domain.award.service.goods.DistributionBase;
import cn.it.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * @description: 描述类商品，以文字形式展示给用户
 * @Author : 38309
 * @create 2023/9/12 11:13
 */
@Component
public class DescGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        //文字描述所以不需要发奖

        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());

        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.CouponGoods.getCode();
    }

}
