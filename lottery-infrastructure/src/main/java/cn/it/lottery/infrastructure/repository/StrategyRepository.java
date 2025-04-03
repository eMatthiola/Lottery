package cn.it.lottery.infrastructure.repository;

import cn.it.lottery.domain.strategy.model.aggregates.StrategyRich;
import cn.it.lottery.domain.strategy.model.vo.AwardBriefVO;
import cn.it.lottery.domain.strategy.model.vo.StrategyBriefVO;
import cn.it.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import cn.it.lottery.domain.strategy.repository.IStrategyRepository;
import cn.it.lottery.infrastructure.dao.IAwardDao;
import cn.it.lottery.infrastructure.dao.IStrategyDao;
import cn.it.lottery.infrastructure.dao.IStrategyDetailDao;
import cn.it.lottery.infrastructure.po.Award;
import cn.it.lottery.infrastructure.po.Strategy;
import cn.it.lottery.infrastructure.po.StrategyDetail;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 38309
 */
@Component
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
//       出错 IStrategyDao.queryStrategyById(Long strategyId);
//        IStrategyDetailDao.queryStrategyDetailListById(Long strategyId);
        Strategy strategy=strategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetailList =strategyDetailDao.queryStrategyDetailList(strategyId);

        //本质解耦infrastructure and domain
        StrategyBriefVO strategyBriefVO=new StrategyBriefVO();
        //可以使用 BeanUtils.coplyProperties(strategy, strategyBriefVO)、或者基于ASM实现的Bean-Mapping，但在效率上最好的依旧是硬编码
        BeanUtils.copyProperties(strategy,strategyBriefVO);
        List<StrategyDetailBriefVO> strategyDetailBriefVOList = new ArrayList<>();
        for(StrategyDetail strategyDetail:strategyDetailList){
            StrategyDetailBriefVO strategyDetailBriefVO=new StrategyDetailBriefVO();
            BeanUtils.copyProperties(strategyDetail,strategyDetailBriefVO);
            strategyDetailBriefVOList.add(strategyDetailBriefVO);
        }
        return new StrategyRich(strategyId,strategyBriefVO,strategyDetailBriefVOList);
    }

    @Override
    public AwardBriefVO queryAwardInfo(String awardId) {
            //将Award转换为AwardBriefVO
            Award award=awardDao.queryAwardInfo(awardId);
            AwardBriefVO awardBriefVO=new AwardBriefVO();
            awardBriefVO.setAwardId(award.getAwardId());
            awardBriefVO.setAwardName(award.getAwardName());
            awardBriefVO.setAwardType(award.getAwardType());
            awardBriefVO.setAwardContent(award.getAwardContent());
        return awardBriefVO;
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return strategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
//        strategyDetailDao.deductStock(strategyDetailReq);
        StrategyDetail strategyDetailReq=new StrategyDetail();
        strategyDetailReq.setStrategyId(strategyId);
        strategyDetailReq.setAwardId(awardId);
        int count=strategyDetailDao.deductStock(strategyDetailReq);
        return count == 1;
    }
}
