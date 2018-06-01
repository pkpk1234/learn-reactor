package com.ljm.reactor.asyncbridge;

import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-30
 */
public class EchoMethod {
    /**
     * 模拟阻塞方法
     *
     * @param str
     * @param delay
     * @param timeUnit
     * @return
     */
    public static String echoAfterTime(String str, int delay, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str;
    }

}
