package cn.it.lottery.rpc.activity.booth.res;

import cn.it.lottery.common.Result;
import cn.it.lottery.rpc.activity.booth.dto.AwardDTO;

import java.io.Serializable;

/**
 * @ClassName DrawRes
 * @Description 抽奖结果
 * @Author Matthiola
 * @Date 2023/10/20 18:17
 */
public class DrawRes extends Result implements Serializable {

    private AwardDTO awardDTO;
    public DrawRes(String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }
}
