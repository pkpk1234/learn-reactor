package com.ljm.reactor.asyncbridge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-04
 */
public class HomePageServiceFutureWrapper {

    private final HomePageService homePageService;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public HomePageServiceFutureWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    Future<String> getUserInfoAsync() {
        return threadPool.submit(() -> this.homePageService.getUserInfo());
    }

    Future<String> getNoticeAsync() {
        return threadPool.submit(() -> this.homePageService.getNotice());
    }

    Future<String> getTodos(Future<String> userInfoFuture) {
        return threadPool.submit(() -> this.homePageService.getTodos(userInfoFuture.get()));
    }

}
