package com.ljm.reactor.errorhandle;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * 异常时retry，每次retry的流都是新的流
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class Retying {
    public static void main(String[] args) throws InterruptedException {


        //默认异常retry
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .retry(2);
        flux.subscribe(newSub());

        //带条件判断的retry
        System.out.println("-------------------------------------------------");
        Thread.sleep(500);
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .retry(1, e -> e instanceof Exception);

        flux.subscribe(newSub());

    }

    private static Subscriber<String> newSub() {
        return new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("start");
                request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value is " + Integer.parseInt(value));
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Complete");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.err.println(throwable.getMessage());
            }
        };
    }
}
