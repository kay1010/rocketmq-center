package com.fk.consumer.config;

import com.fk.consumer.messageListenner.ConsumerMessageListenner;
import com.fk.consumer.messageListenner.OrderlyMessageListenner;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {
    private Logger log = LoggerFactory.getLogger(ConsumerConfig.class);
    @Bean
    public DefaultMQPushConsumer pushConsumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer=new DefaultMQPushConsumer();
        try {
            defaultMQPushConsumer.setConsumerGroup("consumer_transaction_group");
            defaultMQPushConsumer.setNamesrvAddr("192.1" +
                    "68.170.129:9876;192.168.170.129:9877");
            defaultMQPushConsumer.subscribe("SYNC_TOPIC2","*");
            defaultMQPushConsumer.setMessageListener(new ConsumerMessageListenner());//设置MessageListener实例
            defaultMQPushConsumer.setConsumeThreadMin(1);//消费者最小并发数
            defaultMQPushConsumer.setConsumeThreadMax(10);//消费者最大并发数
            defaultMQPushConsumer.setConsumeMessageBatchMaxSize(2);//每次获取的消息数量
            defaultMQPushConsumer.start();
            log.info("【RocketMQ pushConsumer init】");
        } catch (MQClientException e) {
            log.error("pushConsumer init error :", e);
            throw e;
        }
        return defaultMQPushConsumer;
    }


    @Bean
    public DefaultMQPushConsumer orderlypushConsumer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer=new DefaultMQPushConsumer();
        try {
            defaultMQPushConsumer.setConsumerGroup("consumer_orderly_group");
            defaultMQPushConsumer.setNamesrvAddr("192.168.170.129:9876;192.168.170.129:9877");
            defaultMQPushConsumer.subscribe("ORDERLY_TOPIC","*");
            defaultMQPushConsumer.setMessageListener(new OrderlyMessageListenner());//设置MessageListener实例
            //defaultMQPushConsumer.setConsumeThreadMin(5);//消费者最大并发数
            //defaultMQPushConsumer.setConsumeThreadMax(50);//消费者最小并发数
            defaultMQPushConsumer.start();
            log.info("【RocketMQ orderlypushConsumer init】");
        } catch (MQClientException e) {
            log.error("orderlypushConsumer init error :", e);
            throw e;
        }
        return defaultMQPushConsumer;
    }
}
