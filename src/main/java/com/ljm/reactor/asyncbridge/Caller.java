package com.ljm.reactor.asyncbridge;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.*;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-31
 */
public class Caller {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        blockingCall();
        threadAndCallbackCall();
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

    private static void threadAndCallbackCall() throws InterruptedException {
        //用于让调用者线程等待多个异步任务全部结束
        CountDownLatch ct = new CountDownLatch(3);
        HomePageService homePageService = new HomePageService();
        HomePageServiceThreadsAndCallbackWrapper homePageServiceFutureWrapper
                = new HomePageServiceThreadsAndCallbackWrapper(homePageService);
        //统一的finallyCallback
        Runnable finallyCallback = () -> {
            ct.countDown();
        };
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //获取用户信息
        homePageServiceFutureWrapper.getUserInfoAsync(
                (userInfo) -> {
                    System.out.println(userInfo);
                    //由于获取todo依赖于用户信息，必须在此处调用
                    homePageServiceFutureWrapper.getTodos(userInfo,
                            (todos) -> {
                                System.out.println(todos);
                            }, System.err::println, finallyCallback);
                }, System.err::println, finallyCallback
        );
        //获取notice
        homePageServiceFutureWrapper.getNoticeAsync(System.out::println, System.err::println, finallyCallback);
        //等待异步操作全部结束并统计耗时
        ct.await();
        stopWatch.stop();
        System.out.println("thread and callbakc async call methods costs " + stopWatch.getTime() + " mills");
        //退出JVM线程，触发HomePageServiceThreadsAndCallbackWrapper中线程池的shutdownHook
        System.exit(0);
    }

}
