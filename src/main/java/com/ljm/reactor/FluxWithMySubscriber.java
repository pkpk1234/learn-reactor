package com.ljm.reactor;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class FluxWithMySubscriber {
    public static void main(String[] args) {
        //subscirbe with consumer and error handler and completeConsumer
        System.out.println("example for subscribe with consumer , error handler, completeConsumer" +
                "and customerSubscriber");
        Flux<DivideIntegerSupplier> integerFlux = Flux.just(
                new DivideIntegerSupplier(1, 2),
                new DivideIntegerSupplier(8, 2),
                new DivideIntegerSupplier(20, 10),
                new DivideIntegerSupplier(2, 2)
        );
        MySubscriber<DivideIntegerSupplier> integerMySubscriber = new MySubscriber<>();
        integerFlux.subscribe(
                integer ->  integer.get(),
                throwable -> throwable.getMessage(),
                () -> System.out.println("-- No Error and Finished --"),
                integer -> integerMySubscriber.request(5));
        integerFlux.subscribe(integerMySubscriber);
        System.out.println("this is end of main");
    }
}
