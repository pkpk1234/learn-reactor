package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-06
 */
public class FlatMapOperator {
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("a,b,c", "b,c,d", "a,c,d", "a,d,e")
                //每个元素都是一个Flux
                .map(str -> Flux.fromArray(str.split(",")))
                //展开为一个Flux流
                .flatMap(stringFlux -> stringFlux);
        Flux<String> distinct = flux.distinct();
        distinct.subscribe(System.out::println);
    }
}
