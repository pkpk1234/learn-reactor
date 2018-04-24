package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 和merge类似，唯一区别是排在前面的Publisher总是先返回数据
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class MergeSequential {
    public static void main(String[] args) throws InterruptedException {
        Flux<Long> flux1 = Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1));
        Flux<Long> flux2 = Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1));
        Flux<Long> mergedFlux = Flux.mergeSequential(flux1, flux2);
        mergedFlux.subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
