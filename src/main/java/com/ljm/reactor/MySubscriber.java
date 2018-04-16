package com.ljm.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.SignalType;

/**
 * 自定义的Subscriber
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class MySubscriber<T> extends BaseSubscriber<T> {
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("MySubscriber Subscribed");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println("get value: " + value);
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("No error and complete");
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println("Get Error :" + throwable.getCause());
        super.hookOnError(throwable);
    }

    @Override
    protected void hookOnCancel() {
        System.out.println("Subscribe cancled");
    }

    @Override
    protected void hookFinally(SignalType type) {
        System.out.println("Finnally finished");
    }
}
