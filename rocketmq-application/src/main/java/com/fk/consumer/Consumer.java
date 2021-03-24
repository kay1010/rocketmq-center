package com.fk.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName Consumer
 * @Deacription TODO
 * @Author zsl
 * @Date 2021/3/19 15:34
 * @Version 1.0
 **/
public class Consumer {

    public static void main(String[] args) throws Exception{

        // 1.创建消费者Consumer，制定消费者组名
        // 2.指定Nameserver地址
        // 3.订阅主题Topic和Tag
        // 4.设置回调函数，处理消息
        // 5.启动消费者consumer

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_01");

        consumer.setNamesrvAddr("192.168.100.133:9876;192.168.100.135:9876");

        consumer.subscribe("Topic1","TagA");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt ext : list) {
                    System.out.println(new StringBuffer(String.valueOf(ext.getBody())));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();


    }
}
