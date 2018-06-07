package com.ljm.reactor.asyncbridge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-04
 */
public class HomePageServiceThreadsAndCallbackWrapper {

    private final HomePageService homePageService;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public HomePageServiceThreadsAndCallbackWrapper(HomePageService homePageService) {
        this.homePageService = homePageService;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> threadPool.shutdownNow()));
    }

    void getUserInfoAsync(Consumer<String> sucessCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
            try {
                String userInfo = this.homePageService.getUserInfo();
                sucessCallback.accept(userInfo);
            } catch (Throwable ex) {
                errorCallback.accept(ex);
            } finally {
                finallyCallback.run();
            }

        });
    }

    void getNoticeAsync(Consumer<String> sucessCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
            try {
                String notice = this.homePageService.getNotice();
                sucessCallback.accept(notice);
            } catch (Throwable ex) {
                errorCallback.accept(ex);
            } finally {
                finallyCallback.run();
            }

        });
    }

    void getTodos(String userInfo, Consumer<String> sucessCallback, Consumer<Throwable> errorCallback, Runnable finallyCallback) {
        threadPool.submit(() -> {
            try {
                String todos = this.homePageService.getTodos(userInfo);
                sucessCallback.accept(todos);
            } catch (Throwable ex) {
                errorCallback.accept(ex);
            } finally {
                finallyCallback.run();
            }

        });
    }

}
