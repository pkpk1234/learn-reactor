package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

import java.util.function.Predicate;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-01
 */
public class AnyOperator {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(0, 10).log();
        Predicate<Integer> anyBiggerThan9 = integer -> integer > 9;
        flux.any(anyBiggerThan9).subscribe();

        Predicate<Integer> anyBiggerThan5 = integer -> integer > 5;
        flux.any(anyBiggerThan5).subscribe();
    }
}
