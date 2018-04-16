package com.ljm.reactor.backpressure;

import reactor.core.publisher.Flux;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class BackpressureDemo {
    public static void main(String[] args) {
        //可以观察到明显的限流
        Flux.range(0,30000)
                .log()
                .subscribe(new MyLimitedSubscriber(3000));

        //使用比count还大的limiter，相当于不限流
        System.out.println("use big limiter");
        Flux.range(0,30000)
                .log()
                .subscribe(new MyLimitedSubscriber(300000));
    }
}
