package com.gyc.mq.test;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * @author yc.guo@zuche.com on 2017/3/10.
 */
public class RocketMqProducer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("10.104.90.179:9876");
        producer.setVipChannelEnabled(false);
        try {
            producer.start();

            Message msg = new Message("TopicTest",
                    "tag",
                    "Just for test.".getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

//            msg = new Message("PushTopic",
//                    "push",
//                    "2",
//                    "Just for test.".getBytes());
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                    " result:" + result.getSendStatus());
//
//            msg = new Message("PullTopic",
//                    "pull",
//                    "1",
//                    "Just for test.".getBytes());
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                    " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            producer.shutdown();
        }
    }
}
