package com.ljm.reactor.operators;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-27
 */
public class BufferOnHotStream {
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

        hotFlux.buffer(5).subscribe(new BaseSubscriber<List<String>>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(20);
            }

            @Override
            protected void hookOnNext(List<String> value) {
                System.out.println("get value " + value);

            }
        });
        future.thenRun(() -> hotSource.onComplete());
        future.join();
    }
}
