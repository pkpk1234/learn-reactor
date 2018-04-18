package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;

/**
 * 异常默认返回值
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class StaticFallbackValue {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(0)
                .map(i -> 1 / i)
                //异常时返回0
                .onErrorReturn(0);
        //输出应该为0
        flux.log().subscribe(System.out::println);
    }
}
