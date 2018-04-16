package com.ljm.reactor;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class FluxWithLog {
    public static void main(String[] args) {
        //subscirbe with consumer and error handler
        System.out.println("example for subscribe with consumer and error handler");
        Flux<DivideIntegerSupplier> integerFluxWithException = Flux.just(
                new DivideIntegerSupplier(1, 2),
                new DivideIntegerSupplier(8, 2),
                new DivideIntegerSupplier(20, 10),
                new DivideIntegerSupplier(1, 0), //异常数据
                new DivideIntegerSupplier(2, 2)
        ).log();
        integerFluxWithException.subscribe(
                integer -> integer.get(),
                throwable -> System.out.println("get error" + throwable.getMessage())
        );
    }
}
