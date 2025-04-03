package cn.it.lottery.application.process.draw.res;

import cn.it.lottery.common.Result;
import cn.it.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * @ClassName DrawProcessResult
 * @Description 活动抽奖结果
 * @Author Matthiola
 * @Date 2023/10/8 15:16
 */
public class DrawProcessResult extends Result {

    private DrawAwardVO drawAwardVO;

    public DrawProcessResult(String code, String info) {
        super(code, info);
    }

    public DrawProcessResult(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }
}
