package com.fk.producer.controller;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ProducerController {
    private static Logger log = LoggerFactory.getLogger(ProducerController.class);

    @Resource
    private MQProducer defaultMQProducer;

    @Resource
    private TransactionMQProducer payTransactionMQProducer;

    @Resource
    private TransactionMQProducer cancelOrderTransactionMQProducer;

    /**
     * 异步方式发送，非阻塞，回调通知发送结果
     */
    @GetMapping("sendMsg_Async")
    public void sendMsg() {
        Message message = new Message("ASYNC_TOPIC", ("Async msg").getBytes(StandardCharsets.UTF_8));
        try {
            //基于回调的异步方式 发送消息
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步方式消息发送【ASYAN_TOPIC】成功,sendResult：{}", JSON.toJSONString(sendResult));
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("异步方式消息发送异常", throwable);
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步方式发送普通消息
     */
    @GetMapping("sendMsg_Sync")
    public void sendMsgSync() {
       for(int i=1;i<=4;i++){
           Message message = new Message("SYNC_TOPIC2", "syncTag", ("Sync msg"+i).getBytes(StandardCharsets.UTF_8));
           SendResult result = null;
           try {
               result = defaultMQProducer.send(message);
           } catch (Exception e) {
               e.printStackTrace();
           }
           log.info("同步方式发送消息返回结果 result：{}", JSON.toJSONString(result));
       }

    }

    /**
     * 单向发送，不考虑结果
     */
    @GetMapping("sendMsg_oneway")
    public void sendMsgOneWay() {
        Message message = new Message("ONEWAY_TOPIC", ("oneway msg").getBytes(StandardCharsets.UTF_8));
        try {
            defaultMQProducer.sendOneway(message);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 顺序消息
     */
    @GetMapping("sendOrderlyMsg")
    public void sendOrderlyMsg() {
        try {
            for (int i = 1; i < 10; i++) {
                Message message = new Message("ORDERLY_TOPIC", "orderly_tag", ("orderly msg" +i).getBytes(StandardCharsets.UTF_8));
                SendResult sendResult=defaultMQProducer.send(message, new SelectMessageQueueByHash(), "orderly");
                log.info("----sendResult: {}",JSON.toJSONString(sendResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /**
     * 支付订单事务消息
     *
     * @throws InterruptedException
     */
    @GetMapping("paySuccessTransactionMsg")
    public void paySuccessSendTransactionMsg() throws InterruptedException {
        Message message = new Message("PAY_TRANSACTION_TOPIC", "transactionTag", ("pay success !").getBytes(StandardCharsets.UTF_8));
        try {
            TransactionSendResult result = payTransactionMQProducer.sendMessageInTransaction(message, null);
            log.info("sendTransactionMsg 发送事务消息结果 result：{}", JSON.toJSONString(result));
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订单事务消息
     *
     * @throws InterruptedException
     */
    @GetMapping("CancelOrderTransactionMsg")
    public void sendCancelOrderTransactionMsg() throws InterruptedException {
        Message message = new Message("CANCEL_TRANSACTION_TOPIC", "transactionTag", ("pay success !").getBytes(StandardCharsets.UTF_8));
        try {
            TransactionSendResult result = cancelOrderTransactionMQProducer.sendMessageInTransaction(message, null);
            log.info("sendTransactionMsg 发送事务消息结果 result：{}", JSON.toJSONString(result));
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }



}
