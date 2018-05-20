package com.ljm.reactor.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class BackpressureOnBackpressureError {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = hotSource
                .publish()
                .autoConnect()
                .onBackpressureError();

        CompletableFuture future = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 50).parallel().forEach(
                    value -> {
                        threadPool.submit(() -> hotSource.onNext("value is " + value));
                    }
            );
        });
        System.out.println("future run");

        hotFlux.subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value " + value);
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        Thread.sleep(500);
        System.out.println("shutdown");
        threadPool.shutdownNow();
    }
}
