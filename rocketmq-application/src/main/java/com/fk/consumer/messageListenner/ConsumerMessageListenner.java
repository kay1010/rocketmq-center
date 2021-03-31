package com.fk.consumer.messageListenner;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 可并发消费,可重试3次的MessageListenner
 */
public class ConsumerMessageListenner implements MessageListenerConcurrently {
    private static Logger logger = LoggerFactory.getLogger(ConsumerMessageListenner.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        MessageExt messageExt = null;
        for (int i = 0; i < msgs.size(); i++) {
            try {
                Thread.sleep(1000);
                messageExt = msgs.get(i);
            } catch (Exception e) {
                logger.info("消息消费异常：{},消息内容：{}",e.getMessage(),new String(messageExt.getBody()));
                if (messageExt.getReconsumeTimes() >= 3) {//消费重试3次后如果仍失败就不再进行重试
                    logger.info("重试三次不再重试:{}",new String(messageExt.getBody()));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;//消费失败
            }
        }
        logger.info("消息消费成功：{}", new String(messageExt.getBody()));
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//消费成功
    }
}
