package com.ljm.reactor;

import reactor.core.publisher.Flux;

import java.util.Arrays;

/**
 * 最简单的Flux构建方法：使用Flux的静态工厂方法
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-15
 */
public class SimplestFlux {
    public static void main(String[] args) {
        Flux<String> stringFlow = Flux.just("one","two","three");
        Flux<Integer> numberFlow = Flux.fromIterable(Arrays.asList(1,2,3));
    }
}
