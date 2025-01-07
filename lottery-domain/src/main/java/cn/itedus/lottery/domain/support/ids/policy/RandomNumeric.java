package cn.itedus.lottery.domain.support.ids.policy;

import cn.itedus.lottery.domain.support.ids.IIdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName RandomNumeric
 * @Description 随机算法，用于生成策略ID
 * @Author Matthiola
 * @Date 2023/9/20 7:35
 */
@Component
public class RandomNumeric implements IIdGenerator {


    @Override
    public long nextId() {
        return Long.parseLong(RandomStringUtils.randomNumeric(11));
    }
}
