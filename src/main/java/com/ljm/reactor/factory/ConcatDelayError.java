package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

/**
 * concatDelayError 和 concat的方法功能相同，唯一不同在于
 * 异常处理。concatDelayError会等待所有的流处理完成之后，再
 * 将异常传播下去。
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class ConcatDelayError {
    public static void main(String[] args) {
        Flux<Integer> sourceWithErrorNumFormat = Flux.just("1", "2", "3", "4", "Five").map(
                str -> Integer.parseInt(str)
        );
        Flux<Integer> source = Flux.just("5", "6", "7", "8", "9").map(
                str -> Integer.parseInt(str)
        );

        Flux<Integer> concated = Flux.concatDelayError(sourceWithErrorNumFormat, source);
        concated.subscribe(new MySubscriber("concatDelayError"));
    }
}
