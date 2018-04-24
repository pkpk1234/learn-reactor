package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * merge和concat方法类似，只是不会依次返回每个Publisher流中数据，
 * 而是哪个Publisher中先有数据生成，就立刻返回。如果发生异常，
 * 会立刻抛出异常并终止。
 *
 * mergeDelayError会等到所有流都complete之后，再传播异常
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class Merge {
    public static void main(String[] args) throws InterruptedException {
        Flux<Long> flux1 = Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1));
        Flux<Long> flux2 = Flux.interval(Duration.ofSeconds(2), Duration.ofSeconds(1));
        Flux<Long> mergedFlux = Flux.merge(flux1, flux2);
        mergedFlux.subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
