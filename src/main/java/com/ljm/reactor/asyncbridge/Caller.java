package com.ljm.reactor.asyncbridge;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-31
 */
public class Caller {
    public static void main(String[] args) {
        blockingCall();
    }

    private static void blockingCall() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(HomePageService.getUserInfo());
        System.out.println(HomePageService.getNotice());
        System.out.println(HomePageService.getTodos());
        stopWatch.stop();
        System.out.println("call methods costs " + stopWatch.getTime() + " mills");
    }
}
