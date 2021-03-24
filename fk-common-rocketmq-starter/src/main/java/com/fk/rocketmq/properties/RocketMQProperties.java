package com.fk.rocketmq.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fk.rocketmq")
public class RocketMQProperties {
    private String enable;
    private String groupName = "default";
    private String namesrvAddr="localhost:9876";
    /**
     * 消息最大长度
     */
    private int producerMaxMessageSize = 1024;
    /**
     * 发送消息超时时间,默认3000
     */
    private int producerSendMsgTimeout = 2000;
    /**
     * 发送消息失败重试次数，默认2
     */
    private int producerRetryTimesWhenSendFailed = 2;
    /**
     * 消费者最小线程数量
     */
    private int consumerConsumeThreadMin = 1;
    private int consumerConsumeThreadMax = 1;
    /**
     * 每次消费消息的条数，默认为1条
     */
    private int consumerConsumeMessageBatchMaxSize = 1;

    @Override
    public String toString() {
        return "RocketMQProperties{" +
                "enable='" + enable + '\'' +
                ", groupName='" + groupName + '\'' +
                ", namesrvAddr='" + namesrvAddr + '\'' +
                ", producerMaxMessageSize=" + producerMaxMessageSize +
                ", producerSendMsgTimeout=" + producerSendMsgTimeout +
                ", producerRetryTimesWhenSendFailed=" + producerRetryTimesWhenSendFailed +
                ", consumerConsumeThreadMin=" + consumerConsumeThreadMin +
                ", consumerConsumeThreadMax=" + consumerConsumeThreadMax +
                ", consumerConsumeMessageBatchMaxSize=" + consumerConsumeMessageBatchMaxSize +
                '}';
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public int getProducerMaxMessageSize() {
        return producerMaxMessageSize;
    }

    public void setProducerMaxMessageSize(int producerMaxMessageSize) {
        this.producerMaxMessageSize = producerMaxMessageSize;
    }

    public int getProducerSendMsgTimeout() {
        return producerSendMsgTimeout;
    }

    public void setProducerSendMsgTimeout(int producerSendMsgTimeout) {
        this.producerSendMsgTimeout = producerSendMsgTimeout;
    }

    public int getProducerRetryTimesWhenSendFailed() {
        return producerRetryTimesWhenSendFailed;
    }

    public void setProducerRetryTimesWhenSendFailed(int producerRetryTimesWhenSendFailed) {
        this.producerRetryTimesWhenSendFailed = producerRetryTimesWhenSendFailed;
    }

    public int getConsumerConsumeThreadMin() {
        return consumerConsumeThreadMin;
    }

    public void setConsumerConsumeThreadMin(int consumerConsumeThreadMin) {
        this.consumerConsumeThreadMin = consumerConsumeThreadMin;
    }

    public int getConsumerConsumeThreadMax() {
        return consumerConsumeThreadMax;
    }

    public void setConsumerConsumeThreadMax(int consumerConsumeThreadMax) {
        this.consumerConsumeThreadMax = consumerConsumeThreadMax;
    }

    public int getConsumerConsumeMessageBatchMaxSize() {
        return consumerConsumeMessageBatchMaxSize;
    }

    public void setConsumerConsumeMessageBatchMaxSize(int consumerConsumeMessageBatchMaxSize) {
        this.consumerConsumeMessageBatchMaxSize = consumerConsumeMessageBatchMaxSize;
    }
}
