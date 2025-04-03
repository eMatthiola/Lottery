package cn.it.lottery.domain.award.service.factory;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.award.service.goods.IDistributionGoods;
import cn.it.lottery.domain.award.service.goods.impl.CouponGoods;
import cn.it.lottery.domain.award.service.goods.impl.DescGoods;
import cn.it.lottery.domain.award.service.goods.impl.PhysicalGoods;
import cn.it.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * @description: 各类发奖奖品配置类
 * @Author : 38309
 * @create 2023/9/12 10:41
 */
public class GoodsConfig {
    /*根据奖品类型创造工厂实例
     * Map K(Constants.GoodType.getCode()) Value(goodType)*/


    static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private DescGoods descGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private PhysicalGoods physicalGoods;


    // 初始化方法
    @PostConstruct
    public void init() {
        goodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(),redeemCodeGoods);
        goodsMap.put(Constants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), physicalGoods);
    }


}
