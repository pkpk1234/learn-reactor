package com.ljm.reactor.asyncbridge;

import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-01
 */
public class HomePageService {
    public static String getUserInfo() {
        return EchoMethod.echoAfterTime("get user info", 50, TimeUnit.MILLISECONDS);
    }

    public static String getNotice() {
        return EchoMethod.echoAfterTime("get notices", 50, TimeUnit.MILLISECONDS);
    }

    public static String getTodos() {
        return EchoMethod.echoAfterTime("get todos", 100, TimeUnit.MILLISECONDS);
    }

}
