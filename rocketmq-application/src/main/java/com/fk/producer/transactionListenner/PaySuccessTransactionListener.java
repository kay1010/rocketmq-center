package com.fk.producer.transactionListenner;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaySuccessTransactionListener implements TransactionListener {
    private static Logger log= LoggerFactory.getLogger(PaySuccessTransactionListener.class);
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            log.info("回调executeLocalTransaction方法, message: {},object: {}",message,o);
            log.info("执行本地事务 与MQ消息关联的订单支付逻辑");
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            log.info("本地事务 支付逻辑执行异常,MQ消息回滚");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("回调checkLocalTransaction方法事务回查,messageExt: {}",messageExt);
        //TODO 检查本地事务是否执行成功，成功返回COMMIT_MESSAGE，否则返回ROLLBACK_MESSAGE
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
