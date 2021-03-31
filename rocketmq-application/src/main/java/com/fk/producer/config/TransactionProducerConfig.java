package com.fk.producer.config;

import com.fk.producer.transactionListenner.CancleOrderTransactionListener;
import com.fk.producer.transactionListenner.PaySuccessTransactionListener;
import com.fk.rocketmq.properties.RocketMQProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * 事务消息TransactionMQProducer配置
 * 由于TransactionListener的存在，TransactionMQProducer是非线程安全的所以每个类型的消息都要单独配置生产者Bean
 */
@Configuration
public class TransactionProducerConfig {
    private Logger log = LoggerFactory.getLogger(TransactionProducerConfig.class);

    @Autowired(required = false)
    private RocketMQProperties rocketMQProperties;//使用starter中的配置类

    @Autowired
    private PaySuccessTransactionListener paySuccessTransactionListener;

    @Autowired
    private CancleOrderTransactionListener cancleOrderTransactionListener;

    /**
     * @return 支付订单事务消息生产者
     * @throws MQClientException
     */
    @Bean
    public TransactionMQProducer payTransactionMQProducer() throws MQClientException {
        TransactionMQProducer transactionMQProducer=new TransactionMQProducer();
       transactionMQProducer.setTransactionListener(paySuccessTransactionListener);
        transactionMQProducer.setProducerGroup("pay_group");
        transactionMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());//nameServer地址
        transactionMQProducer.setMaxMessageSize(rocketMQProperties.getProducerMaxMessageSize());//发送消息内容最大值
        transactionMQProducer.setSendMsgTimeout(rocketMQProperties.getProducerSendMsgTimeout());//发送消息超时时间
        transactionMQProducer.setRetryTimesWhenSendFailed(rocketMQProperties.getProducerRetryTimesWhenSendFailed());//发送失败时重试次数
        try {
            transactionMQProducer.start();
            log.info("【RocketMQ payTransactionMQProducer init】 {}", rocketMQProperties.toString());
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}", e.getMessage(), e));
            throw e;
        }
        return transactionMQProducer;
    }

    /**
     * @return 取消订单事务消息生产者
     * @throws MQClientException
     */
    @Bean
    public TransactionMQProducer cancelOrderTransactionMQProducer() throws MQClientException {
        TransactionMQProducer transactionMQProducer=new TransactionMQProducer();
        transactionMQProducer.setTransactionListener(cancleOrderTransactionListener);
        transactionMQProducer.setProducerGroup(UUID.randomUUID().toString());
        transactionMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        transactionMQProducer.setMaxMessageSize(rocketMQProperties.getProducerMaxMessageSize());
        transactionMQProducer.setSendMsgTimeout(rocketMQProperties.getProducerSendMsgTimeout());//发送消息超时时间
        transactionMQProducer.setRetryTimesWhenSendFailed(rocketMQProperties.getProducerRetryTimesWhenSendFailed());//发送失败时重试次数
        try {
            transactionMQProducer.start();
            log.info("【RocketMQ cancleOrderTransactionMQProducer init】 {}", rocketMQProperties.toString());
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}", e.getMessage(), e));
            throw e;
        }
        return transactionMQProducer;
    }
}
