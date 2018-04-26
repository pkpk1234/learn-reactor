package com.ljm.reactor.setpverifier;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-26
 */
public class SimpleExpect {
    public static void main(String[] args) {
        StepVerifier.create(Flux.just("one", "two","three"))
                //依次校验每一步的数据是否符合预期
                .expectNext("one")
                .expectNext("two")
                .expectNext("three")
                //校验Flux流是否按照预期正常关闭
                .expectComplete()
                //启动
                .verify();

        StepVerifier.create(Flux.just("one", "two","three"))
                //依次校验每一步的数据是否符合预期
                .expectNext("one")
                .expectNext("two")
                //不满足预期，抛出异常
                .expectNext("Five")
                //校验Flux流是否按照预期正常关闭
                .expectComplete()
                //启动
                .verify();
    }
}
