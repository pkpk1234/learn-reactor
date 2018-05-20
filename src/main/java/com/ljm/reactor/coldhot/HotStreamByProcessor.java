package com.ljm.reactor.coldhot;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-20
 */
public class HotStreamByProcessor {
    public static void main(String[] args) throws InterruptedException {
        //使用Reactor提供的Processor工具类
        UnicastProcessor<String> hotSource = UnicastProcessor.create();
        //构造Hot Stream，同时配置为autoConnect，避免每加入一个Subscriber都需要调用一次connect方法
        Flux<String> hotFlux = hotSource
                .publish()
                .autoConnect();

        //异步为Hot Stream提供数据
        CompletableFuture future = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 10).forEach(
                    value -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //调用Processor的onNext即可以为Processor关联的Hot Stream提供数据
                        hotSource.onNext("value is " + value);
                    }
            );
        });

        hotFlux.subscribe(s -> System.out.println("subsciber1: " + s));
        Thread.sleep(500);
        hotFlux.subscribe(s -> System.out.println("subsciber2: " + s));
        //提供完数据之后，调用Processor的onComplete关闭Hot Stream
        future.thenRun(() -> hotSource.onComplete());
        future.join();
    }
}
