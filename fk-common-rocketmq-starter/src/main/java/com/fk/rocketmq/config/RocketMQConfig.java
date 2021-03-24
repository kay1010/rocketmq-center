package com.fk.rocketmq.config;

import com.alibaba.fastjson.JSON;
import com.fk.rocketmq.properties.RocketMQProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
@ConditionalOnProperty(prefix = "fk.rocketmq",name = "enable",havingValue = "true")
public class RocketMQConfig {
    private Logger log = LoggerFactory.getLogger(RocketMQConfig.class);

    @Autowired(required = false)
    private RocketMQProperties rocketMQProperties;

    /**
     * @return 初始化生产者Bean
     * @throws MQClientException
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup("group1");
        //producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        producer.setMaxMessageSize(rocketMQProperties.getProducerMaxMessageSize());
        producer.setSendMsgTimeout(rocketMQProperties.getProducerSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(rocketMQProperties.getProducerRetryTimesWhenSendFailed());
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            log.info("【RocketMQ defaultMQProducer init】 {}", rocketMQProperties.toString());
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}", e.getMessage(), e));
            throw e;
        }
        return producer;
    }
}
