package com.ljm.reactor.operators;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class DelaySubscription {
    public static void main(String[] args) {
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5)
                .delaySubscription(Duration.ofSeconds(2));
        final long startTime = System.currentTimeMillis();
        just.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                super.hookOnSubscribe(subscription);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("delay of Subscription is " + duration + " millis");

            }
        });
        just.blockLast();

    }
}
