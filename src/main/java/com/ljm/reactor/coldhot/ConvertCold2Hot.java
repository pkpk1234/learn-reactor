package com.ljm.reactor.coldhot;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class ConvertCold2Hot {
    public static void main(String[] args) throws InterruptedException {
        ConnectableFlux<Long> flux = Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .publish();
        flux.subscribe(aLong -> {
            System.out.println("subscriber1 ,value is " + aLong);
        });

        Thread.sleep(5000);
        //加入第二个Subscriber之前，需要connect一下
        flux.connect();
        flux.subscribe(aLong -> {
            System.out.println("subscriber2 ,value is " + aLong);
        });
        flux.blockLast();
    }
}
