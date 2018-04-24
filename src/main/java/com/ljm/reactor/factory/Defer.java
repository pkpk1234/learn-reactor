package com.ljm.reactor.factory;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * defer构造出的Flux流，每次调用subscribe方法时，都
 * 会调用Supplier获取Publisher实例作为输入。如果Supplier每次
 * 返回的实例不同，则相当于延迟获取Flux源数据流。如果每次都返回
 * 相同的实例，则和from(Publisher<? extends T> source)效果一样
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class Defer {
    public static void main(String[] args) {
        AtomicInteger subscribeTime = new AtomicInteger(1);
        //实现这一的效果，返回的数据流为1~5乘以当前subscribe的次数
        Supplier<? extends Publisher<Integer>> supplier = () -> {
            Integer[] array = {1, 2, 3, 4, 5};
            int currentTime = subscribeTime.getAndIncrement();
            for (int i = 0; i < array.length; i++) {
                array[i] *= currentTime;
            }
            return Flux.fromArray(array);
        };

        Flux<Integer> deferedFlux = Flux.defer(supplier);

        subscribe(deferedFlux, subscribeTime);
        subscribe(deferedFlux, subscribeTime);
        subscribe(deferedFlux, subscribeTime);
    }

    private static void subscribe(Flux<Integer> deferedFlux, AtomicInteger subscribeTime) {
        System.out.println("Subscribe time is "+subscribeTime.get());
        deferedFlux.subscribe(System.out::println);
    }
}
