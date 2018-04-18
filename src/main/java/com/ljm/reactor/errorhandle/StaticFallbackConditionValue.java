package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据抛出的异常的类型返回不同的值
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class StaticFallbackConditionValue {
    public static void main(String[] args) {
        //根据异常类型进行判断
        Flux<Integer> flux = Flux.just(0)
                .map(i -> 1 / i)
                //ArithmeticException异常时返回1
                .onErrorReturn(NullPointerException.class, 0)
                .onErrorReturn(ArithmeticException.class, 1);
        //输出应该为1
        flux.log().subscribe(System.out::println);

        final String nullStr = null;
        Flux<String> stringFlux = Flux.just("")
                .map(str -> nullStr.toString())
                //NullPointerException异常时返回字符串NullPointerException
                .onErrorReturn(NullPointerException.class, "NullPointerException")
                .onErrorReturn(ArithmeticException.class, "ArithmeticException");
        //输出应该为NullPointerException
        stringFlux.log().subscribe(System.out::println);

        //根据Predicate进行判断
        AtomicInteger index = new AtomicInteger(0);
        Flux.just(0, 1, 2, 3)
                .map(i -> {
                    index.incrementAndGet();
                    return 1 / i;
                })
                .onErrorReturn(NullPointerException.class, 0)
                .onErrorReturn(e -> index.get() < 2, 1)
                .log().subscribe(System.out::println);
    }
}
