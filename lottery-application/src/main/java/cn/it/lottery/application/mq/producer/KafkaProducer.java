package cn.it.lottery.application.mq.producer;

import cn.it.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import cn.it.lottery.domain.activity.model.vo.InvoiceVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


import javax.annotation.Resource;

/**
 * @ClassName KafkaProducer
 * @Description
 * @Author Matthiola
 * @Date 2023/10/22 13:48
 */
@Component
public class KafkaProducer {

    /**
     * MQ主题：中奖发货单
     *
     *
     * cd H:\Kafka\kafka_2.13-2.8.0
     * 启动zk：
     * .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
     * 启动kafka：
     * .\bin\windows\kafka-server-start.bat .\config\server.properties
     * 创建topic：.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_invoice
     */

    private Logger logger =LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_INVOICE = "lottery_invoice1";

    /**
     * MQ主题：活动领取记录
     *
     * 创建topic：.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_activity_partake
     *
     */
    public static final String TOPIC_ACTIVITY_PARTAKE = "lottery_activity_partake";


    /**
     * 发送中奖物品发货单消息
     *
     * @param invoiceVO 发货单
     */
    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoiceVO){
        //invoiceVO to JavaScript Object Notation
        String objJson = JSON.toJSONString(invoiceVO);
        logger.info("发送MQ消息 topic：{} bizId：{} message：{}",TOPIC_INVOICE, invoiceVO.getuId(), objJson);
        // 发送消息
        return kafkaTemplate.send(TOPIC_INVOICE, objJson);
    }

    /**
     * 发送领取活动记录MQ
     *
     * @param activityPartakeRecordVO 领取活动记录
     */
    public ListenableFuture<SendResult<String, Object>> sendLotteryActivityPartakeRecord(ActivityPartakeRecordVO activityPartakeRecordVO) {
        String objJson = JSON.toJSONString(activityPartakeRecordVO);
        logger.info("发送MQ消息(领取活动记录) topic：{} bizId: {} message: {}", TOPIC_ACTIVITY_PARTAKE,activityPartakeRecordVO.getuId(), objJson);
        return kafkaTemplate.send(TOPIC_ACTIVITY_PARTAKE, objJson);
    }
}
