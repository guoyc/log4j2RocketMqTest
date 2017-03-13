package com.gyc.log4j.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yc.guo@zuche.com on 2017/3/10.
 */
public class Log4jTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        LOGGER.error("123412312");
        LOGGER.info("123412312");
    }
}
