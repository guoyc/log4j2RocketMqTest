package com.gyc.mq.mian;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.gyc.mq.consumer.ConsumerFactory;
import com.gyc.mq.listener.SendToESListener;

/**
 * @author yc.guo@zuche.com on 2017/3/16.
 */
public class Consumer4SendToESMain {

    private static final String TOPIC = "TopicTest";
    private static final String TAG = "tag";

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = ConsumerFactory.buildMQPushConsumer();
        try {
            consumer.subscribe(TOPIC, TAG);
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new SendToESListener());
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
