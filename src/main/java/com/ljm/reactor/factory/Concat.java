package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

/**
 * concat 及其重载方法接收 多个Flux拼接为一个Flux返回，
 * 返回元素时首先返回接收到的第一个Flux流中的元素，直到第一个Flux
 * 流结束之后，才开始返回第二个Flux流中的元素，依次类推...
 * 如果发生异常，Flux流会立刻异常终止
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class Concat {
    public static void main(String[] args) {
        Flux<Integer> source1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> source2 = Flux.just(6, 7, 8, 9, 10);

        Flux<Integer> concated = Flux.concat(source1, source2);
        concated.subscribe(new MySubscriber("concated"));

    }

}
