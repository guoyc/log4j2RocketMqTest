package com.gyc.mq.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gyc.bean.LogBean;
import com.gyc.elasticsearch.client.SendLogClient;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author yc.guo@zuche.com on 2017/3/16.
 */
public class SendToESListener implements MessageListenerConcurrently {

    private SendLogClient sendLogClient;

    public SendToESListener() {
        try {
            sendLogClient = new SendLogClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        Message msg = msgs.get(0);
        try {
            sendLogClient.addDocument(buildLogBean(msg));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private LogBean buildLogBean(Message msg) {
        String body = new String(msg.getBody());
        LogBean logBean = new LogBean();
        logBean.setMessage(body);
        logBean.setHost("test");
        logBean.setPath("testPath");
        return logBean;
    }
}
