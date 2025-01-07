package cn.itedus.lottery.domain.activity.service.stateflow;

import cn.itedus.lottery.common.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName StateConfig
 * @Description Maps the state of the activity to the corresponding state handler.
 * @Author Matthiola
 * @Date 2023/9/19 12:55
 */

public class StateConfig {
    protected Map<Enum<Constants.ActivityState>, AbstractState> stateGroup = new ConcurrentHashMap<>();

    @Resource
    private AbstractState arraignmentState;

    @Resource
    private AbstractState closeState;

    @Resource
    private AbstractState doingState;

    @Resource
    private AbstractState editingState;

    @Resource
    private AbstractState openState;

    @Resource
    private AbstractState passState;

    @Resource
    private AbstractState refuseState;



    @PostConstruct
    public void init() {
               stateGroup.put(Constants.ActivityState.ARRAIGNMENT, arraignmentState);
               stateGroup.put(Constants.ActivityState.CLOSE, closeState);
               stateGroup.put(Constants.ActivityState.DOING, doingState);
               stateGroup.put(Constants.ActivityState.EDIT, editingState);
               stateGroup.put(Constants.ActivityState.OPEN, openState);
               stateGroup.put(Constants.ActivityState.PASS, passState);
               stateGroup.put(Constants.ActivityState.REFUSE, refuseState);
       }


}
