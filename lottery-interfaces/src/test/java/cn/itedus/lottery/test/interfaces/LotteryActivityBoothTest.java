package cn.itedus.lottery.test.interfaces;


import cn.itedus.lottery.rpc.activity.booth.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.activity.booth.req.DrawReq;
import cn.itedus.lottery.rpc.activity.booth.req.QuantificationDrawReq;
import cn.itedus.lottery.rpc.activity.booth.res.DrawRes;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @ClassName LotteryActivityBoothTest
 * @Description
 * @Author Matthiola
 * @Date 2023/10/21 11:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LotteryActivityBoothTest {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityBoothTest.class);
    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void test_doDraw(){
        DrawReq drawReq = new DrawReq();
        drawReq.setActivityId(100002L);
        drawReq.setuId("liyouwei");
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        logger.info("请求参数：{}", JSON.toJSONString(drawReq));
        logger.info("测试结果：{}", JSON.toJSONString(drawRes));
    }

    @Test
    public void test_doQuantificationDraw(){
        QuantificationDrawReq quantificationDrawReq = new QuantificationDrawReq();
        quantificationDrawReq.setuId("xiaofuge");
        quantificationDrawReq.setTreeId(2110081902L);
        quantificationDrawReq.setValMap(new HashMap<String,Object>(){
            {
                put("gender", "man");
                put("age", "18");
            }
        });
        DrawRes drawRes = lotteryActivityBooth.doQualificationDraw(quantificationDrawReq);
        logger.info("请求参数：{}", JSON.toJSONString(quantificationDrawReq));
        logger.info("测试结果：{}", JSON.toJSONString(drawRes));
    }

}
