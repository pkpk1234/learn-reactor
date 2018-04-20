package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class ConcatWith {
    public static void main(String[] args) {
        Flux<String> just = Flux.just("a", "b", "c").concatWith(Flux.just("d", "e", "f"));
        StepVerifier.create(just)
                .expectNext("a")
                .expectNext("b")
                .expectNext("c")
                .expectNext("d")
                .expectNext("e")
                .expectNext("f")
                .verifyComplete();
    }
}
