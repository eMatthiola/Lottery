package cn.it.lottery.rpc.activity.booth.req;

import java.util.Map;

/**
 * @ClassName QuantificationDrawReq
 * @Description
 * @Author Matthiola
 * @Date 2023/10/20 18:21
 */
public class QuantificationDrawReq {

    /** 用户ID */
    private String uId;
    /** 规则树ID */
    private Long treeId;
    /** 决策值 */
    private Map<String, Object> valMap;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Map<String, Object> getValMap() {
        return valMap;
    }

    public void setValMap(Map<String, Object> valMap) {
        this.valMap = valMap;
    }
}
