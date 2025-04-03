package cn.it.lottery.domain.strategy.service.draw;

import cn.it.lottery.domain.strategy.model.req.DrawReq;
import cn.it.lottery.domain.strategy.model.res.DrawResult;

/**
 * @author 38309
 */
public interface IDrawExec {

    //req
    //response
    DrawResult doDrawExec(DrawReq req) ;
}
