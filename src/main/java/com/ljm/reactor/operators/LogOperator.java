package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

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

        Flux.just(1, 2, 3, 4, 5)
                //使用自定义日志配置
                .log("myCategory")
                .subscribe();

        Flux.just(1, 2, 3, 4, 5)
                //使用自定义日志配置，仅仅关注onComplete信号
                //注意Level类型是java.util.logging.Level
                .log("myCategory", Level.WARNING, SignalType.ON_COMPLETE)
                .subscribe();
    }
}
