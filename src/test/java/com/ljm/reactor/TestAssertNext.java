package com.ljm.reactor;

import org.junit.Test;

import static org.junit.Assert.*;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-26
 */
public class TestAssertNext {
    @Test
    public void test() {
        Flux<Integer> just = Flux.just(1, 2, 3);
        StepVerifier.create(just)
                .assertNext(integer -> {
                    assertEquals(1, integer.intValue());
                }).assertNext(integer -> {
            assertEquals(2, integer.intValue());
        }).assertNext(integer -> {
            assertEquals(3, integer.intValue());
        }).verifyComplete();
    }

}
