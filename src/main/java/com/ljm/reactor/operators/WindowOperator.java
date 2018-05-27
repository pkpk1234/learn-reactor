package com.ljm.reactor.operators;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-27
 */
public class WindowOperator {
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


        hotFlux.window(5).subscribe(new BaseSubscriber<Flux<String>>() {
            int windowIndex = 0;
            int elementIndex = 0;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(20);
            }

            @Override
            protected void hookOnNext(Flux<String> value) {
                value.subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println(String.format("Start window %d", windowIndex));

                        requestUnbounded();
                    }

                    @Override
                    protected void hookOnNext(String value) {
                        System.out.println(String.format("Element %d is %s", elementIndex, value));
                        elementIndex++;
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println(String.format("Finish window %d", windowIndex));
                        windowIndex++;
                        elementIndex = 0;
                    }
                });


            }
        });
        future.thenRun(() -> hotSource.onComplete());
        future.join();
    }
}
