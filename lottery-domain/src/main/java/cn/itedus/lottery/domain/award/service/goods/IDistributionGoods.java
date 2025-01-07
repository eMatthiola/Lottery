package cn.itedus.lottery.domain.award.service.goods;

import cn.itedus.lottery.domain.award.model.req.GoodsReq;
import cn.itedus.lottery.domain.award.model.res.DistributionRes;

/**
 * Created by IntelliJ IDEA.
 * @description: 抽奖，抽象出配送货物接口，把各类奖品模拟成货物、配送代表着发货，包括虚拟奖品和实物奖品
 * @Author : 38309
 * @create 2023/9/12 9:34
 */
public interface IDistributionGoods {

    //发送奖品

    /*1.根据奖品类型创造工厂实例
    * 2.发送奖品给顾客*/

    /**
     * 奖品配送接口，奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     *
     * @param req   物品信息
     * @return      配送结果
     */
    DistributionRes doDistribution(GoodsReq req);

    //更新奖品数据
    Integer getDistributionGoodsName();

}
