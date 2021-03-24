# rocketmq-center

## fk-common-rokcetmq-starter
  
  基于rocketmq-client 4.5.1 做了配置文件的自动配置,SPI自动配置全局的 DefaultMQProducer Bean, 由于TransactionMQProducer不能复用 不做全局Bean配置
  
## rocketmq-application
  
  基于starter实现producer生产者发送消息,包括单向发送,同步发送,异步发送,事务模式发送;事务模式需要单独配置多个不同的TransactionMQProducer Bean 供不同场景单独使用

