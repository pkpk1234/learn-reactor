package com.ljm.reactor.asyncbridge;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.*;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-31
 */
public class Caller {
    public static void main(String[] args) throws InterruptedException {
        blockingCall();
        //threadAndCallbackCall();
        //completableFutureCall();
        publisherCall();
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
        HomePageServiceThreadsAndCallbackWrapper homePageServiceThreadsAndCallbackWrapper
                = new HomePageServiceThreadsAndCallbackWrapper(homePageService);
        //统一的finallyCallback
        Runnable finallyCallback = () -> {
            ct.countDown();
        };
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //获取用户信息
        homePageServiceThreadsAndCallbackWrapper.getUserInfoAsync(
                (userInfo) -> {
                    System.out.println(userInfo);
                    //由于获取todo依赖于用户信息，必须在此处调用
                    homePageServiceThreadsAndCallbackWrapper.getTodos(userInfo,
                            (todos) -> {
                                System.out.println(todos);
                            }, System.err::println, finallyCallback);
                }, System.err::println, finallyCallback
        );
        //获取notice
        homePageServiceThreadsAndCallbackWrapper.getNoticeAsync(System.out::println, System.err::println, finallyCallback);
        //等待异步操作全部结束并统计耗时
        ct.await();
        stopWatch.stop();
        System.out.println("thread and callback async call methods costs " + stopWatch.getTime() + " mills");
        //退出JVM线程，触发HomePageServiceThreadsAndCallbackWrapper中线程池的shutdownHook
        System.exit(0);
    }

    private static void completableFutureCall() throws InterruptedException {
        //用于让调用者线程等待多个异步任务全部结束
        CountDownLatch ct = new CountDownLatch(2);
        HomePageService homePageService = new HomePageService();
        HomePageSerivceCompletableFutureWrapper homePageSerivceCompletableFutureWrapper =
                new HomePageSerivceCompletableFutureWrapper(homePageService);
        //统一的finallyCallback
        Runnable finallyCallback = () -> {
            ct.countDown();
        };
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        homePageSerivceCompletableFutureWrapper
                .getUserInfoAsync()
                //依赖调用
                .thenCompose(userInfo -> {
                    System.out.println(userInfo);
                    return homePageSerivceCompletableFutureWrapper.getTodosAsync(userInfo);
                })
                .thenAcceptAsync(System.out::println)
                .thenRun(finallyCallback);

        homePageSerivceCompletableFutureWrapper
                .getNoticeAsync()
                .thenAcceptAsync(System.out::println)
                .thenRun(finallyCallback);
        //等待异步操作全部结束并统计耗时
        ct.await();
        stopWatch.stop();
        System.out.println("CompletableFuture async call methods costs " + stopWatch.getTime() + " mills");

    }

    private static void publisherCall() throws InterruptedException {
        //用于让调用者线程等待多个异步任务全部结束
        CountDownLatch ct = new CountDownLatch(2);
        //统一的finallyCallback
        Runnable finallyCallback = () -> {
            ct.countDown();
        };
        StopWatch stopWatch = new StopWatch();
        HomePageService homePageService = new HomePageService();
        HomePageServicePublisherWrapper homePageServicePublisherWrapper =
                new HomePageServicePublisherWrapper(homePageService);
        homePageServicePublisherWrapper
                .getUserInfoAsync()
                //由于初始化线程池很耗时，所以将stopWatch放置到此处
                //真是系统中，线程池应该提前初始化，而不应该用于一次性的方法
                .doOnSubscribe(subscription -> {
                    stopWatch.start();
                })
                //消费userInfo
                .doOnNext(System.out::println)
                //调用依赖于userInfo的getTodos
                .flatMap((userInfo) -> homePageServicePublisherWrapper.getTodosAsync(userInfo))
                //消费todos
                .doOnNext(System.out::println)
                .doFinally(s -> finallyCallback.run())
                .subscribe();

        homePageServicePublisherWrapper
                .getNoticeAsync()
                .doOnNext(System.out::println)
                .doFinally((s) -> {
                    finallyCallback.run();
                })
                .subscribe();
        ct.await();
        stopWatch.stop();
        System.out.println("Publisher async call methods costs " + stopWatch.getTime() + " mills");
    }
}
