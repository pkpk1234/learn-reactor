package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-23
 */
public class Range {
    public static void main(String[] args) {
        Flux.range(1, 5).subscribe(System.out::println);
        Flux.range(Integer.MAX_VALUE, 5).subscribe(System.out::println);
    }
}
