package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;

/**
 * 使用Flux.error或者onErrorMap捕获了异常之后重新抛出
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class ReThrow {
    public static void main(String[] args) {
        //Flux.error
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(sourceEx -> Flux.error(
                        new RuntimeException(sourceEx)
                ));
        //异常打印的异常类型应该为java.lang.RuntimeException
        flux.subscribe(System.out::println, System.err::println);

        //onErrorMap
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorMap(sourceEx ->
                        new RuntimeException(sourceEx));
        //异常打印的异常类型应该为java.lang.RuntimeException
        flux.subscribe(System.out::println, System.err::println);

        //根据异常类型判断转换为哪种类型的异常
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorMap(NumberFormatException.class, sourceEx ->
                        new RuntimeException(sourceEx));
        //异常打印的异常类型应该为java.lang.RuntimeException
        flux.subscribe(System.out::println, System.err::println);

        //根据Predicate判断转换为哪种类型的异常
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorMap(e -> e instanceof NumberFormatException, sourceEx ->
                        new RuntimeException(sourceEx));
        //异常打印的异常类型应该为java.lang.RuntimeException
        flux.subscribe(System.out::println, System.err::println);
    }
}
