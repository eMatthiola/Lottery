package cn.it.lottery.domain.rule.service.logic.impl;

import cn.it.lottery.domain.rule.model.req.DecisionMatterReq;
import cn.it.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserGenderFilter
 * @Description
 * @Author Matthiola
 * @Date 2023/10/19 21:39
 */
@Component
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
