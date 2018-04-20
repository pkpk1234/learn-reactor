package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 和集合相关的操作
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class CollectOps {
    private static Integer[] integers = {1, 2, 3, 4, 5, 1, 2, 3};

    public static void main(String[] args) {
        Flux<Integer> just = Flux.just(integers);

        //计算元素总数
        count(just);
    }

    private static void count(Flux<Integer> just) {
        StepVerifier.create(just.count())
                .expectNext((long) integers.length);
    }
}
