package cn.it.lottery.test;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.award.model.req.GoodsReq;
import cn.it.lottery.domain.award.model.res.DistributionRes;
import cn.it.lottery.domain.award.service.factory.DistributionGoodsFactory;
import cn.it.lottery.domain.award.service.goods.IDistributionGoods;
import cn.it.lottery.domain.strategy.model.req.DrawReq;
import cn.it.lottery.domain.strategy.model.res.DrawResult;
import cn.it.lottery.domain.strategy.model.vo.DrawAwardVO;
import cn.it.lottery.domain.strategy.service.draw.IDrawExec;
import cn.it.lottery.infrastructure.dao.IActivityDao;
import cn.it.lottery.infrastructure.po.Activity;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRunnerTest {

    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IDrawExec drawExec;

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
    }

    @Test
    public void test_award() {
        // 1.执行抽奖
        DrawResult drawResult=drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));

        //2.判断抽奖结果是否为空
        if (Constants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())) {
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        //3.分发奖品
        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardVO drawAwardInfo =drawResult.getDrawAwardInfo();
        GoodsReq goodsReq= new GoodsReq(drawResult.getuId(),2109313442431L,drawAwardInfo.getAwardId(),drawAwardInfo.getAwardName(),drawAwardInfo.getAwardContent());

        //调工厂方法,创建对应的奖品
        IDistributionGoods distributionGoods=new DistributionGoodsFactory().getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes distributionRes =distributionGoods.doDistribution(goodsReq);

        logger.info("测试结果：{}",JSON.toJSONString(distributionRes));
    }

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("xiaofuge");
        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.queryActivityById(100001L);
        logger.info("测试结果：{}", JSON.toJSONString(activity));
    }

}
