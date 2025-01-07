package cn.itedus.lottery.domain.activity.model.aggregates;

import cn.itedus.lottery.domain.activity.model.vo.ActivityVO;
import cn.itedus.lottery.domain.activity.model.vo.AwardVO;
import cn.itedus.lottery.domain.activity.model.vo.StrategyVO;

import java.util.List;

/**
 * @ClassName ActivityRich
 * @Description 聚合VO对象
 * @Author Matthiola
 * @Date 2023/9/18 10:48
 */
public class ActivityConfigRich {
    /**
     * 活动配置
     */
    private ActivityVO activityVO;
    /**
     * 奖品配置
     */
    private List<AwardVO> awardList;
    /**
     * 策略配置
     */
    private StrategyVO strategyVO;

    public ActivityConfigRich(ActivityVO activityVO, List<AwardVO> awardList, StrategyVO strategyVO) {
        this.activityVO = activityVO;
        this.awardList = awardList;
        this.strategyVO = strategyVO;
    }

    public ActivityVO getActivityVO() {
        return activityVO;
    }

    public void setActivityVO(ActivityVO activityVO) {
        this.activityVO = activityVO;
    }

    public List<AwardVO> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<AwardVO> awardList) {
        this.awardList = awardList;
    }

    public StrategyVO getStrategyVO() {
        return strategyVO;
    }

    public void setStrategyVO(StrategyVO strategyVO) {
        this.strategyVO = strategyVO;
    }
}
