package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

/**
 * doOnXXX回调
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-21
 */
public class DoOnX {
    public static void main(String[] args) {
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        doOnEach(just);
        doOnNext(just);
        doOnRequst(just);
    }

    private static void doOnEach(Flux<Integer> just) {
        just.doOnEach(integerSignal -> {
            System.out.println("doOnEach " + integerSignal.get());
        }).subscribe(System.out::println);
    }

    private static void doOnNext(Flux<Integer> just) {
        //doOnNext中对元素的修改是不会影响到Flux中元素的值的
        just.doOnNext(integer -> {
            System.out.println("doOnNext " + (++integer));
        }).subscribe(System.out::println);
    }

    private static void doOnRequst(Flux<Integer> just) {
        //doOnRequest可以获取到Subscriber的request(N)中的N
        just.doOnRequest(l -> System.out.println("doOnRequest " + l))
                .subscribe(System.out::println);
    }
}
