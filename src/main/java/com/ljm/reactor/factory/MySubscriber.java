package com.ljm.reactor.factory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class MySubscriber implements Subscriber<Integer> {
    private String name;

    public MySubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println(this.name + " onSubscribe");
        subscription.request(Integer.MAX_VALUE);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(this.name + " onError: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(this.name + " onComplete");
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println(this.name + "get value " + integer);
    }
}
