package com.gyc.elasticsearch.client;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gyc.bean.LogBean;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yc.guo@zuche.com on 2017/3/16.
 */
public class SendLogClient {

    /**
     * es服务器的host
     */
    private static final String HOST = "10.104.90.179";

    /**
     * es服务器暴露给client的port
     */
    private static final int PORT = 9300;

    private static final String LOG_INDEX_PRE = "log-";
    private static final String TYPE = "logs";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
    private static final String LOG_MAPPING = "{\"logs\": { \"properties\":{\"path\": {\"type\": \"string\",\"analyzer\":\"ik_max_word\"},\"host\": {\"type\": \"string\",\"analyzer\":\"ik_max_word\"},\"message\": {\"type\": \"string\",\"analyzer\":\"ik_max_word\"}}}}";

    private TransportClient transportClient;

    public SendLogClient() throws UnknownHostException {
        transportClient = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));
    }

    public void addDocument(LogBean LogBean)
            throws UnknownHostException, JsonProcessingException {
        String index = buildIndex();
        createIndexAndMapping(index);
        byte[] json = JSONObject.toJSONString(LogBean).getBytes();
        transportClient.prepareIndex(index, TYPE).setSource(json).get();
//        transportClient.close();
    }

    public void createIndexAndMapping(String index) {
        // 没有索引的情况
        if (!(transportClient.admin().indices().prepareExists(index).get().isExists())) {
            System.out.println(LOG_MAPPING);
            transportClient.admin().indices().prepareCreate(index).addMapping(TYPE, LOG_MAPPING).get();
        }
    }

    private String buildIndex() {
        Date date = new Date(System.currentTimeMillis());
        String strDate = formatter.format(date);
        return LOG_INDEX_PRE + strDate;
    }

    public static void main(String[] args) throws UnknownHostException, JsonProcessingException {
        SendLogClient sendLogClient = new SendLogClient();
        LogBean logBean = new LogBean();
        logBean.setHost("123a");
        logBean.setPath("123a");
        logBean.setMessage("123a");
        sendLogClient.addDocument(logBean);
//        sendLogClient.createIndexAndMapping();
    }
}
