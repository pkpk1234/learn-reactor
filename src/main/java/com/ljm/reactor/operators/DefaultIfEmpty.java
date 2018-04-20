package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class DefaultIfEmpty {
    public static void main(String[] args) {
        Flux<List<Integer>> flux =
                //构建一个奇数序列
                Flux.just(1, 3, 5, 7, 9)
                //只buffer偶数，所以不会返回任何数
                .bufferWhile(integer -> integer % 2 == 0)
                //默认返回一个空List
                .defaultIfEmpty(Collections.emptyList());
        StepVerifier.create(flux)
                .expectNext(Collections.emptyList())
                .verifyComplete();
    }
}
