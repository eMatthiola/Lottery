package cn.it.lottery.test.domain;

import cn.it.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.it.lottery.domain.rule.model.res.EngineResult;
import cn.it.lottery.domain.rule.service.engine.EngineFilter;
import cn.it.lottery.infrastructure.dao.RuleTreeNodeLineDao;
import cn.it.lottery.infrastructure.po.RuleTreeNodeLine;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName RuleTest
 * @Description
 * @Author Matthiola
 * @Date 2023/10/20 14:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RuleTest {

    private Logger logger = LoggerFactory.getLogger(ActivityTest.class);
    @Resource
    private EngineFilter engineFilter;

    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Test
    public  void test_lineDao(){
        RuleTreeNodeLine ruleTreeNodeLine = new RuleTreeNodeLine();
        ruleTreeNodeLine.setNodeIdFrom(Long.valueOf("1"));
        ruleTreeNodeLine.setTreeId(2110081902L);
        List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.queryRuleTreeNodeLineList(ruleTreeNodeLine);
        int i = ruleTreeNodeLineDao.queryTreeNodeLineCount(2110081902L);

        if (ruleTreeNodeLineList == null || ruleTreeNodeLineList.size() == 0){
            throw new RuntimeException("ruleTreeNodeLineList is null");
        }


        logger.info("查询结果：{}", JSON.toJSONString(ruleTreeNodeLineList));
        logger.info("查询结果：{}", JSON.toJSONString(i));


    }

    @Test
    public void test_process(){
        DecisionMatterReq req = new DecisionMatterReq();
        req.setTreeId(2110081902L);
        req.setUserId("fustack");
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "25");
        }});

        EngineResult res = engineFilter.process(req);

        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(res));

    }
}
