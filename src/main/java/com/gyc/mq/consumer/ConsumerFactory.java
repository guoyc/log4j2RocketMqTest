package com.gyc.mq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * @author yc.guo@zuche.com on 2017/3/16.
 */
public class ConsumerFactory {

    private static final String CONSUMER_GROUP = "Consumer";
    private static final String MQ_HOST = "10.104.90.179:9876";

    public static DefaultMQPushConsumer buildMQPushConsumer() {
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(MQ_HOST);
        // 一定要加这个 从rocketMQ3.4以后会默认开启VIP通道 10911-12 = 10909会出错
        consumer.setVipChannelEnabled(false);
        return consumer;
    }
}
