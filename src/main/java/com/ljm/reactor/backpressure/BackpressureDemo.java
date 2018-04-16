package com.ljm.reactor.backpressure;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class BackpressureDemo {
    public static void main(String[] args) {
        Flux.range(0,1000)
                .log()
                .subscribe(new MyLimitedSubscriber(100));
    }
}
