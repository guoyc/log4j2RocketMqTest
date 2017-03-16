package com.gyc.log4j.test;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yc.guo@zuche.com on 2017/3/10.
 */
public class Log4jTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jTest.class);
    private static final int loopTimes = 1;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        MDC.put("ip", "123.123.123.123");
        for (int i = 0; i < loopTimes; i ++) {
            LOGGER.error("这是第" + i + "数据");
        }
        System.out.println("一共用了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }
}
