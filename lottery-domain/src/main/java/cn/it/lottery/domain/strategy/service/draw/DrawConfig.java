package cn.it.lottery.domain.strategy.service.draw;

import cn.it.lottery.common.Constants;
import cn.it.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 将算法写入到Map中,通过1/2 调用
 * @author 38309
 */
public class DrawConfig {
    @Resource
    private IDrawAlgorithm singeAlgorithm;
    @Resource
    private IDrawAlgorithm rangeMatchAlgorithm;

    protected static Map<Integer,IDrawAlgorithm> drawAlgorithmMap=new ConcurrentHashMap<>();

    //@PostConstruct项目启动的时候执行这个方法
    @PostConstruct
    public void init(){
        drawAlgorithmMap.put(Constants.StrategyMode.SINGLE.getCode(), singeAlgorithm);
        drawAlgorithmMap.put(Constants.StrategyMode.ENTIERTY.getCode(), rangeMatchAlgorithm);
    }

}
