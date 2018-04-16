package com.ljm.reactor.backpressure;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */

public class MyLimitedSubscriber<T> extends BaseSubscriber<T> {
    private RateLimiter rateLimiter;
    private long mills;

    public MyLimitedSubscriber(double permitsPerSecond) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        this.mills = System.currentTimeMillis();
        requestUnbounded();
    }

    @Override
    protected void hookOnComplete() {
        long now = System.currentTimeMillis();
        long time = now - this.mills;
        System.out.println("cost time:" + time / 1000 + " seconds");
    }

    @Override
    protected void hookOnNext(T value) {
        if (rateLimiter.tryAcquire()) {
            rateLimiter.acquire();
            requestUnbounded();
        } else {
            System.out.println("too fast");
            System.out.println("current value is " + value);
            request(1);
        }
    }
}
