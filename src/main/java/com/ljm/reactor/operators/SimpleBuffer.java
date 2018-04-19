package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * buffer方法重载
 * 其中skip更像step
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-19
 */
public class SimpleBuffer {

    private static Flux<String> just = Flux.just("a", "b", "c", "d", "e");

    public static void main(String[] args) {
        buffer();
        bufferWithMax();
        bufferWithMaxAndSkipAndMaxBiggerThanSkip();
        System.out.println("All test success.");
    }

    private static void buffer() {
        StepVerifier.create(
                just.buffer()
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")))
                .verifyComplete();
    }

    private static void bufferWithMax() {
        StepVerifier.create(
                //buffer 大小为2，第一次返回ab，第二次返回cd，第三次返回e
                just.buffer(2)
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b")))
                .expectNext(new ArrayList<>(Arrays.asList("c", "d")))
                .expectNext(new ArrayList<>(Arrays.asList("e")))
                .verifyComplete();
    }

    private static void bufferWithMaxAndSkipAndMaxBiggerThanSkip() {
        StepVerifier.create(
                just.buffer(2, 1)
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b")))
                .expectNext(new ArrayList<>(Arrays.asList("b", "c")))
                .expectNext(new ArrayList<>(Arrays.asList("c", "d")))
                .expectNext(new ArrayList<>(Arrays.asList("d", "e")))
                .expectNext(new ArrayList<>(Arrays.asList("e")))
                .verifyComplete();
    }
}
