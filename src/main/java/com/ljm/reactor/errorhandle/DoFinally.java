package com.ljm.reactor.errorhandle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.function.Consumer;

/**
 * onFinally：类似于Finally语句，无论如何ON_COMPLETE都会触发
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-18
 */
public class DoFinally {
    public static void main(String[] args) {
        Consumer<SignalType> finallyConsumer = signalType -> {
            switch (signalType) {
                case ON_COMPLETE: {
                    System.out.println("Successfully complete");
                    break;
                }
                case CANCEL: {
                    System.out.println("Canceled");
                    break;
                }
                case ON_ERROR: {
                    System.err.println("System Error");
                    break;
                }
            }
        };

        //判断发生了异常
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e.printStackTrace())
                .onErrorReturn("System exception")
                .doFinally(finallyConsumer);
        //最后打印System Error
        flux.log().subscribe(System.out::println);

        //判断正常完成
        flux = Flux.just("0", "1", "2")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e.printStackTrace())
                .onErrorReturn("System exception")
                .doFinally(finallyConsumer);
        //最后打印Successfully complete
        flux.log().subscribe(System.out::println);

        //判断cancel
        flux = Flux.just("0", "1", "2")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e.printStackTrace())
                .onErrorReturn("System exception")
                .doFinally(finallyConsumer);
        //最后打印Canceled
        flux
                .log()
                //take(1)会在处理1个元素之后cancel
                .take(1)
                .subscribe(System.out::println);
    }
}
