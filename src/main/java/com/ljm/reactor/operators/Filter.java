package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-21
 */
public class Filter {
    public static void main(String[] args) {
        Flux<Integer> just = Flux.range(1, 10);

        /**
         * filter的过程为：
         *    req(1)---> <predicate>--true-->emitted返回元素给Subscriber-->req(1)... 不断循环这个过程直到Flux结束
         *                    |
         *                    false
         *                    |-->drop-->req(1)...
         */
        filter(just);

        /**
         * filterWhen的过程类似，不过将emitted这一步修改为
         * 放入buffer中，直到流结束将整个buffer返回
         */
        filterWhen(just);
    }

    private static void filter(Flux<Integer> just) {
        StepVerifier.create(just.filter(integer -> integer % 2 == 0).log())
                .expectNext(2)
                .expectNext(4)
                .expectNext(6)
                .expectNext(8)
                .expectNext(10)
                .verifyComplete();
    }

    private static void filterWhen(Flux<Integer> just) {
        StepVerifier.create(just
                .filterWhen(v -> Mono.just(v % 2 == 0)).log())
                //一次性返回
                .expectNext(2, 4, 6, 8, 10)
                .verifyComplete();

    }
}
