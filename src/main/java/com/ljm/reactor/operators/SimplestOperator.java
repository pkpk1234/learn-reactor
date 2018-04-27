package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-27
 */
public class SimplestOperator {
    public static void main(String[] args) {
        //源Flux实例
        Flux<Integer> sourceFlux = Flux.range(1, 100);
        //添加filter Operators之后，返回新的Flux实例
        Flux<Integer> filteredFlux = sourceFlux.filter(integer -> (integer % 2 == 0 && integer % 7 == 0));
        //添加sort Operators之后，返回新的Flux实例
        Flux<Integer> sortedFlux = filteredFlux.sort((j, k) -> {
            if (k < j) {
                return 1;
            } else if (k.equals(j)) {
                return 0;
            } else {
                return -1;
            }
        });
        //调用subscribe终结链条
        sortedFlux.subscribe(System.out::println);

        //每个作为输入的流都没有被修改

        //源Flux中数据量应为99
        StepVerifier.create(sourceFlux.count()).expectNext(99L);
        //中间状态的filteredFlux是没有被逆序的
        StepVerifier.create(filteredFlux)
                .expectNext(14, 28, 42, 56, 70, 84, 98);
    }
}
