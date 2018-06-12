package com.ljm.reactor.parallel;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-12
 */
public class FluxParallel {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(
                        Thread.currentThread().getName() + " -> " + i)
                );
    }
}
