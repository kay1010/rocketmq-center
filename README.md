# rocketmq-center

- fk-common-rokcetmq-starter：基于rocketmq-client 4.5.1 做了配置文件的自动配置,SPI自动配置全局的 DefaultMQProducer Bean, 由于TransactionMQProducer不能复用 不做全局Bean配置

- rocketmq-application：基于starter实现producer生产者发送消息,单独配置不同的TransactionMQProducer Bean 实现事务消息发送

