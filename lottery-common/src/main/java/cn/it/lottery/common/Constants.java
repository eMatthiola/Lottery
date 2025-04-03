package cn.it.lottery.common;



/**
 * @author 38309
 */
public class Constants {

    public enum ResponseCode{

//        Java 枚举,必须放这个位置
        SUCCESS("0000", "successful!"),

        UN_ERROR("0001","未知失败"),
        ILLEGAL_PARAMETER("0002","非法参数"),
        INDEX_DUP("0003","主键冲突"),
        NO_UPDATE("0004","SQL操作无更新"),

        LOSING_DRAW("D001", "未中奖"),
        RULE_ERR("D002", "量化人群规则执行失败"),
        NOT_CONSUMED_TAKE("D003", "未消费活动领取记录"),
        OUT_OF_STOCK("D004", "活动无库存"),
        ERR_TOKEN("D005", "分布式锁失败");
        ;



        private String code;

        private String info;


        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }


    }

    public enum StrategyMode{
        SINGLE(1,"单项概率"),
        ENTIERTY(2,"总体概率");

        private Integer code;
        private String info;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        StrategyMode(Integer code, String info) {
            this.code = code;
            this.info = info;

        }
    }


    public enum DrawState{
        FAIL(0,"未中奖"),
        SUCCESS(1,"已中奖"),
        COVER(2,"兜底奖");
        private Integer code;
        private String info;

        DrawState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public enum AwardType{

        /*1:文字描述、2:兑换码、3:优惠券、4:实物奖品*/
        DESC(1, "文字描述"),
        RedeemCodeGoods(2, "兑换码"),
        CouponGoods(3, "优惠券"),
        PhysicalGoods(4, "实物奖品");


        private Integer code;
        private String info;

        AwardType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }



    }

    public enum AwardState{
        /**
         * 等待发奖
         */
        WAIT(0, "等待发奖"),

        /**
         * 发奖成功
         */
        SUCCESS(1, "发奖成功"),

        /**
         * 发奖失败
         */
        FAILURE(2, "发奖失败");

        ;
        private Integer code;
        private String info;

        AwardState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public enum ActivityState{
         /** 1：编辑 */
        EDIT(1, "编辑"),
        /** 2：提审 */
        ARRAIGNMENT(2, "提审"),
        /** 3：撤审 */
        REVOKE(3, "撤审"),
        /** 4：通过 */
        PASS(4, "通过"),
        /** 5：运行(活动中) */
        DOING(5, "运行(活动中)"),
        /** 6：拒绝 */
        REFUSE(6, "拒绝"),
        /** 7：关闭 */
        CLOSE(7, "关闭"),
        /** 8：开启 */
        OPEN(8, "开启");

        private Integer code;
        private String info;

        ActivityState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public enum Ids{
        /** 雪花算法 */
        SnowFlake,
        /** 日期算法 */
        ShortCode,
        /** 随机算法 */
        RandomNumeric;
    }

    /**
     * 发奖状态 0初始、1完成、2失败
     */
    public enum GrantState{

        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        GrantState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 决策树节点
     */
    public static final class NodeType{
        /** 树茎 */
        public static final Integer STEM = 1;
        /** 果实 */
        public static final Integer FRUIT = 2;
    }


    /**
     * 规则限定类型
     */
    public static final class RuleLimitType {
        /** 等于 */
        public static final int EQUAL = 1;
        /** 大于 */
        public static final int GT = 2;
        /** 小于 */
        public static final int LT = 3;
        /** 大于&等于 */
        public static final int GE = 4;
        /** 小于&等于 */
        public static final int LE = 5;
        /** 枚举 */
        public static final int ENUM = 6;
    }



    /**
     * 全局属性
     */
    public static final class Global {
        /** 空节点值 */
        public static final Long TREE_NULL_NODE = 0L;
    }



    /**
     * 消息发送状态（0未发送、1发送成功、2发送失败）
     */
    public enum MQState {
        INIT(0, "初始"),
        COMPLETE(1, "完成"),
        FAIL(2, "失败");

        private Integer code;
        private String info;

        MQState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }


    /**
     * 活动单使用状态 0未使用、1已使用
     */
    public enum TaskState {

        NO_USED(0, "未使用"),
        USED(1, "已使用");

        private Integer code;
        private String info;

        TaskState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }


    public static final class RedisKey{

        // 抽奖活动库存 Key
        public static final String LOTTERY_ACTIVITY_STOCK_COUNT = "lottery_activity_stock_count_";

       public static String KEY_LOTTERY_ACTIVITY_STOCK_COUNT(Long activityId){
           return LOTTERY_ACTIVITY_STOCK_COUNT + activityId;
       }

        // 抽奖活动库存锁 Key
        public static final String LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN = "lottery_activity_stock_count_token_";

        public static String KEY_LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN(Long activityId, Integer stockUsedCount){
            return LOTTERY_ACTIVITY_STOCK_COUNT_TOKEN + activityId + "_" + stockUsedCount;
        }
    }

}
