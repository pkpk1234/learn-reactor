package com.ljm.reactor.factory;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-24
 */
public class FromPublisher {
    public static void main(String[] args) {
        Publisher<Integer> fluxPublisher = Flux.just(1, 2, 3);
        Publisher<Integer> monoPublisher = Mono.just(0);

        System.out.println("Flux from flux");
        Flux.from(fluxPublisher).subscribe(System.out::println);

        System.out.println("Flux from mono");
        Flux.from(monoPublisher).subscribe(System.out::println);
    }
}
