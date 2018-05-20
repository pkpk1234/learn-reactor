package com.ljm.reactor.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class BackPressureOnBackpressureDrop {
    public static void main(String[] args) {
        onBackpressureLatest();
    }

    private static void onBackpressureLatest() {
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = getHotFlux(hotSource);
        CompletableFuture future = produceData(hotSource);
        //构建Subscriber，初次请求20个元素
        BaseSubscriber<String> subscriber = createSubscriber(20);
        hotFlux.subscribe(subscriber);

        future.join();
        System.out.println("get rest elements");
        //再次获取10个元素，根据策略应返还最后的10个元素
        subscriber.request(10);
    }

    private static BaseSubscriber<String> createSubscriber(int initRequests) {
        return new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(initRequests);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value " + value);
            }
        };
    }

    private static CompletableFuture produceData(UnicastProcessor<String> hotSource) {
        return CompletableFuture.runAsync(() -> {
            IntStream.range(0, 50).forEach(
                    value -> {
                        hotSource.onNext("value is " + value);
                    }
            );
        });
    }

    private static Flux<String> getHotFlux(UnicastProcessor hotSource) {
        return hotSource
                .publish()
                .autoConnect()
                .onBackpressureDrop();
    }
}
