package com.ljm.reactor.backpressure;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */

public class MyLimitedSubscriber<T> extends BaseSubscriber<T> {
    private RateLimiter rateLimiter;
    public MyLimitedSubscriber(double permitsPerSecond) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println("get value " + value);
        if (rateLimiter.tryAcquire()) {
            rateLimiter.acquire();
            requestUnbounded();
        } else {
            System.out.println("too fast");
            request(1);
        }
    }
}
