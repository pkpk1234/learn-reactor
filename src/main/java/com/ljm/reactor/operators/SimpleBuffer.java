package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * buffer方法重载
 * 
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-19
 */
public class SimpleBuffer {
    public static void main(String[] args) {
        StepVerifier.create(
                Flux.just("a", "b", "c", "d", "e").buffer()
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e")))
                .verifyComplete();
    }
}
