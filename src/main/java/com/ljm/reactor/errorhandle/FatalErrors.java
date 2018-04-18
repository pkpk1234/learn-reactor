package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;

/**
 * 有一些异常、错误，Reactor是不会传播，而是直接抛出的
 * 这些异常由 Exceptions.throwIfFatal 方法判断
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class FatalErrors {
    public static void main(String[] args) {
        Flux.just("")
                .map(s -> {
                    throw new UnknownError(s);
                })
                .doOnError(throwable -> System.err.println("doOnError "+throwable.getMessage()))
                .onErrorReturn("UnknownError")
                .log()
                //不会传播异常，即不会触发doOnError和onErrorReturn，而是直接抛出
                .subscribe(System.out::println, System.err::println);
    }
}
