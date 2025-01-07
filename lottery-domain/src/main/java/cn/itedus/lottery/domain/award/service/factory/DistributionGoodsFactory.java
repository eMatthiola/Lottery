package cn.itedus.lottery.domain.award.service.factory;

import cn.itedus.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * @description: 配送商品简单工厂，提供获取配送服务
 * @Author : 38309
 * @create 2023/9/12 13:00
 */
 @Service
public class DistributionGoodsFactory extends GoodsConfig {

    public IDistributionGoods getDistributionGoodsService(Integer awardType){
    return goodsMap.get(awardType);
    }
}
