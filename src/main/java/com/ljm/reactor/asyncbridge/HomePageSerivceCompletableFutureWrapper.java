package com.ljm.reactor.asyncbridge;

import java.util.concurrent.CompletableFuture;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-08
 */
public class HomePageSerivceCompletableFutureWrapper {
    private final HomePageService homePageService;

    public HomePageSerivceCompletableFutureWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    CompletableFuture<String> getUserInfoAsync() {
        return CompletableFuture.supplyAsync(this.homePageService::getUserInfo);
    }

    CompletableFuture<String> getNoticeAsync() {
        return CompletableFuture.supplyAsync(this.homePageService::getNotice);
    }

    CompletableFuture<String> getTodosAsync(String userInfo) {
        return CompletableFuture.supplyAsync(() -> this.homePageService.getTodos(userInfo));
    }
}
