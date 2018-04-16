package com.ljm.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Flux上重载的各自subscribe方法
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */

@Slf4j
public class FluxSubScribes {
    public static void main(String[] args) {
        Flux<String> stringFlow = Flux.just("one", "two", "three");

        //subscribe
        System.out.println("example for subscribe");
        stringFlow.subscribe();

        //subscribe with consumer
        System.out.println("example for subscribe with consumer");
        stringFlow.subscribe(System.out::println);

        //subscirbe with consumer and error handler
        System.out.println("example for subscribe with consumer and error handler");
        Flux<DivideIntegerSupplier> integerFluxWithException = Flux.just(
                new DivideIntegerSupplier(1, 2),
                new DivideIntegerSupplier(8, 2),
                new DivideIntegerSupplier(20, 10),
                new DivideIntegerSupplier(1, 0), //异常数据
                new DivideIntegerSupplier(2, 2)
        );
        integerFluxWithException.subscribe(
                integer -> System.out.println("get integer:" + integer.get()),
                throwable -> System.out.println("get error" + throwable.getMessage())
        );

        //subscirbe with consumer and error handler and completeConsumer
        System.out.println("example for subscribe with consumer , error handler and completeConsumer");
        Flux<DivideIntegerSupplier> integerFlux = Flux.just(
                new DivideIntegerSupplier(1, 2),
                new DivideIntegerSupplier(8, 2),
                new DivideIntegerSupplier(20, 10),
                new DivideIntegerSupplier(2, 2)
        );
        integerFlux.subscribe(
                integer -> System.out.println("get integer:" + integer.get()),
                throwable -> System.out.println("get error" + throwable.getMessage()),
                () -> System.out.println("No Error and Finished"));


    }
}
