package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class DelayElement {
    public static void main(String[] args) {
        Flux<Tuple2<Long, Integer>> just = Flux.just(0, 1, 2, 3, 4, 5)
                .elapsed()
                //使用Scheduler，默认使用Schedulers.parallel()
                .delayElements(Duration.ofSeconds(1));
        just.subscribe(System.out::println);
        //由于使用了Scheduler，所以Flux和Subscriber运行的线程和主线程不同
        //此处必须bockLast，否则主线程退出，不会看到打印的数据
        just.blockLast();
    }
}
