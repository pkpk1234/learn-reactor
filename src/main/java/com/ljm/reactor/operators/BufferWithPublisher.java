package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * buffer入参包含了Publisher，publisher触发buffer中数据
 * push给Subscriber
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-19
 */
public class BufferWithPublisher {
    private static Flux<String> just = Flux.just("a", "b", "c", "d", "e");

    //TODO: 这个例子有问题，找下解决方法
    public static void main(String[] args) {
        Flux<Integer> other = Flux.just(1, 2);

        just.buffer(other)
                .doOnComplete(() -> System.out.println("complete"))
                .publishOn(Schedulers.elastic())
                .subscribe(System.out::println, System.err::println);
        other.subscribe(System.out::println, System.err::println);
    }
}
