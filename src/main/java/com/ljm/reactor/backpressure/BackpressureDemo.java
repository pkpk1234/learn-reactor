package com.ljm.reactor.backpressure;

import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class BackpressureDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //可以观察到明显的限流
        Flux<Integer> flux = Flux.range(0, 50).doOnComplete(() -> countDownLatch.countDown());
        flux.subscribe(new MyLimitedSubscriber(5));
        countDownLatch.await();

        //使用比count还大的limiter，相当于不限流
        System.out.println("use big limiter");
        Flux.range(0, 50)
                .subscribe(new MyLimitedSubscriber(100));
    }
}
