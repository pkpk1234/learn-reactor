package com.ljm.reactor.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.publisher.WorkQueueProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class BackpressureOnBackpressureBuffer {
    public static void main(String[] args) {

        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        Flux<String> hotFlux = hotSource
                .publish()
                .autoConnect()
                .onBackpressureBuffer(10);

        CompletableFuture future = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 50).forEach(
                    value -> {
                        hotSource.onNext("value is " + value);
                    }
            );
        });

        hotFlux.subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(20);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value " + value);

            }
        });
        future.thenRun(() -> hotSource.onComplete());
        future.join();
    }
}
