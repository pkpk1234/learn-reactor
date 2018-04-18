package com.ljm.reactor.errorhandle;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 使用方法返回值作为异常时的返回值
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class FallbackMethod {
    private static Function<? super Throwable, ? extends Publisher<String>> fallback
            = e -> Mono.just(e.getMessage());

    public static void main(String[] args) {
        //1. 默认方法
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(e -> Mono.just("input string is not a number ," + e.getMessage()));
        flux.log().subscribe(System.out::println);

        //2. 根据异常类型选择返回方法
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(ArithmeticException.class, e -> Mono.just("ArithmeticException:" + e.getMessage()))
                .onErrorResume(NumberFormatException.class, e -> Mono.just("input string is not a number"))
                //如果上面列出的异常类型都不满足，使用默认方法
                .onErrorResume(e -> Mono.just(e.getMessage()));
        // 因为异常类型为NumberFormatException，此处应该打印字符串input string is not a number
        flux.log().subscribe(System.out::println);

        //3. 根据Predicate选择返回方法
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(e -> e.getMessage().equals("For input string: \"abc\""),
                        e -> Mono.just("exception data is abc"))
                //onErrorResume可以和onErrorReturn混合使用
                .onErrorReturn("SystemException");
        //因为判断条件，此处应该打印exception data is abc
        flux.log().subscribe(System.out::println);
    }
}
