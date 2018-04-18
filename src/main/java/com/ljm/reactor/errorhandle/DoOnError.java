package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;

/**
 * doOnError：只是想获取异常进行处理，并不想返回数据，并且
 * 不会阻止异常传播
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class DoOnError {
    public static void main(String[] args) {

        //1. 默认doOnError方法
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e.printStackTrace())
                .onErrorReturn("System exception");
        flux.log().subscribe(System.out::println);

        //2. 根据异常类型选择doError方法
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(RuntimeException.class, e -> {
                    System.err.println("发生了RuntimeException");
                    e.printStackTrace();
                })
                .doOnError(NumberFormatException.class, e -> {
                    System.err.println("发生了NumberFormatException");
                    e.printStackTrace();
                })
                .onErrorReturn("System exception");
        //因为异常类型为NumberFormatException，此处应打印字符串发生了NumberFormatException
        //又因为doOnError不会阻止异常传播，所以onErrorReturn会执行，返回字符串System exception
        flux.log().subscribe(System.out::println);

        //3. 根据Predicate选择doError方法
        //   注意doOnError不会阻止异常传播，所以onErrorReturn可以多次触发
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e instanceof Throwable, e -> {
                    System.err.println("异常类型为Throwable");
                })
                .doOnError(e -> e instanceof Exception, e -> {
                    System.err.println("同时异常类型为Exception");
                })
                .doOnError(e -> e instanceof NumberFormatException, e -> {
                    System.err.println("并且异常类型为NumberFormatException");
                })
                .doOnError(e -> e instanceof Error, e -> {
                    System.err.println("异常类型为Error");
                })
                .onErrorReturn("System exception");
        //因为异常类型为NumberFormatException，所以前面3个doOnError都会被调用
        flux.log().subscribe(System.out::println);
    }
}
