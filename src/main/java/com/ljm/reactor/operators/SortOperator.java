package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-01
 */
public class SortOperator {
    public static void main(String[] args) {
        Flux<Long> flux = Flux.interval(Duration.ofMillis(100)).sort().log();
        flux.subscribe(
                System.out::print, System.err::println
        );
        flux.blockLast();
    }
}
