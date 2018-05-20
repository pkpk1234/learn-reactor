package com.ljm.reactor.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class BackpressureOnBackpressureBufferTimeout {
    public static void main(String[] args) throws InterruptedException {

        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = hotSource
                .publish()
                .autoConnect()
                .publishOn(Schedulers.parallel())
                .onBackpressureBuffer(Duration.ofMillis(1000), 5, s -> {
                });

        CompletableFuture future = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 50).forEach(
                    value -> {
                        hotSource.onNext("value is " + value);
                    }
            );
        });

        BaseSubscriber<String> subscriber = new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value " + value);

            }
        };
        hotFlux.subscribe(subscriber);
        future.join();
        System.out.println("get rest elements from buffer");
        //再次获取10个元素，根据策略应返还最后的10个元素
        subscriber.request(10);
    }
}
