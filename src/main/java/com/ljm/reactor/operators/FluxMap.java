package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-17
 */
public class FluxMap {
    public static void main(String[] args) {
        mapAfterPublishOn();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        mapBeforeSubscribeOn();
    }

    /**
     * publishOn之后的operator都会使用指定的Scheduler
     * log()会打印出线程名
     */
    private static void mapAfterPublishOn() {
        Flux flux = Flux.range(1, 100)
                .distinct()
                .publishOn(Schedulers.elastic())
                .map(integer -> integer * 2)
                .log();
        flux.subscribe(System.err::println);
        flux.blockLast();
    }

    /**
     * subscribeOn之后的operator都会使用指定的Scheduler
     * log()会打印出线程名
     */
    private static void mapBeforeSubscribeOn() {
        Flux flux = Flux.range(1, 100)
                .map(integer -> integer * 2)
                .subscribeOn(Schedulers.elastic())
                .log();
        flux.subscribe(System.err::println);
        flux.blockLast();
    }
}
