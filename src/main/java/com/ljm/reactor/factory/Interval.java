package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class Interval {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1)).subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
