package com.fk.consumer.messageListenner;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *顺序消费的MessageListenner
 */
public class OrderlyMessageListenner implements MessageListenerOrderly {
    private static Logger logger = LoggerFactory.getLogger(OrderlyMessageListenner.class);
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        try {
            for(MessageExt msg:msgs){
                logger.info("顺序消费 接收消息：{}",new String(msg.getBody()));
            }
        }catch (Exception e){
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
