package com.ljm.reactor.factory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-23
 */
public class SpecialFactory {
    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                subscription.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext value is " + o);

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError exception message is " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        System.out.println("start empty---------");
        Flux.empty().subscribe(subscriber);

        System.out.println("start error---------");
        Flux.error(new RuntimeException("my exception")).subscribe(subscriber);

        System.out.println("start never---------");
        Flux.never().subscribe(subscriber);
    }
}
