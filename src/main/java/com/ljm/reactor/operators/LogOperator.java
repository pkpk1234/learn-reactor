package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-28
 */
public class LogOperator {
    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, 5)
                //日志记录详细的执行步骤
                .log()
                .subscribe();
    }
}
