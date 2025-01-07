package cn.itedus.lottery.test.application;

import cn.itedus.lottery.application.process.draw.req.DrawProcessReq;
import cn.itedus.lottery.application.process.draw.IActivityDrawProcess;
import cn.itedus.lottery.application.process.draw.res.DrawProcessResult;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName ActivityProcessTest
 * @Description ActivityProcessTest
 * @Author Matthiola
 * @Date 2023/10/8 16:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {
    private Logger logger = LoggerFactory.getLogger(ActivityProcessTest.class);

    @Resource
    private IActivityDrawProcess activityProcess;



    @Test
    public void test_doDrawProcess(){
        DrawProcessReq drawProcessReq = new DrawProcessReq();
        drawProcessReq.setActivityId(100002L);
        drawProcessReq.setuId("li");

        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(drawProcessReq);

        logger.info("请求入参：{}", JSON.toJSONString(drawProcessReq));
        logger.info("测试结果：{}", JSON.toJSONString(drawProcessResult));
    }





}
