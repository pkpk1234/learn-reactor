package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-06
 */
public class ReduceOperator {
    public static void main(String[] args) {
        Flux<Integer> source = Flux.range(0, 100);

        //使用迭代方式求和
        final AtomicInteger sum = new AtomicInteger(0);
        source.subscribe(integer ->
                sum.getAndAdd(integer)
        );
        System.out.println(sum.get());

        //reduce方式求和
        source.reduce((i, j) -> i + j)
                .subscribe(System.out::println);
        
        //reduce方式求和，并且指定reduce的初始值
        source.reduce(100, (i, j) -> i + j)
                .subscribe(System.out::println);

    }
}
