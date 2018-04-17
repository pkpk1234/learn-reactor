package com.ljm.reactor.scheduler;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-17
 */
public class FluxPublishOn {
    public static void main(String[] args) {
        Flux flux = Flux.range(1, 1000)
                .publishOn(Schedulers.parallel())
                .log();
        flux.subscribe();
        //如果使用了Scheduler，则subscribe是异步的，主线程必须阻塞才行
        System.out.println(Thread.currentThread().getName() + "-Main thread blocking");
        flux.blockLast();
        System.out.println(Thread.currentThread().getName() + "-Flow complete,Main thread run and finished");

    }
}
