package com.ljm.reactor.asyncbridge;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-31
 */
public class Caller {
    public static void main(String[] args) {
        blockingCall();
    }

    private static void blockingCall() {
        HomePageService homePageService = new HomePageService();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String userInfo = homePageService.getUserInfo();
        System.out.println(userInfo);
        System.out.println(homePageService.getNotice());
        System.out.println(homePageService.getTodos(userInfo));
        stopWatch.stop();
        System.out.println("call methods costs " + stopWatch.getTime() + " mills");
    }

}
