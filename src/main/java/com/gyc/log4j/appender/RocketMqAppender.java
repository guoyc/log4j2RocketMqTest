package com.gyc.log4j.appender;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Date;

/**
 * @author yc.guo@zuche.com on 2017/3/11.
 */
public class RocketMqAppender extends AppenderSkeleton {

    private String rocketMqHost;
    private String topic;
    private String tag;
    private DefaultMQProducer producer;

    @Override
    protected void append(LoggingEvent event) {
        String message = subAppend(event);
        LogLog.debug("[" + new Date(event.getTimeStamp()) + "]" + message);
        try {

            Message msg = new Message(getTopic(),
                    getTag(),
                    message.getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult result = producer.send(msg);
            LogLog.debug("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        producer.shutdown();
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void activateOptions() {
        producer = buildProducer();
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    private String subAppend(LoggingEvent event) {
        return (this.layout == null) ? event.getRenderedMessage() : this.layout.format(event);
    }

    private DefaultMQProducer buildProducer() {
        DefaultMQProducer result = new DefaultMQProducer("Producer4Log4j");
        result.setNamesrvAddr(getRocketMqHost());
        result.setVipChannelEnabled(false);
        return result;
    }

    public String getRocketMqHost() {
        return rocketMqHost;
    }

    public void setRocketMqHost(String rocketMqHost) {
        this.rocketMqHost = rocketMqHost;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
