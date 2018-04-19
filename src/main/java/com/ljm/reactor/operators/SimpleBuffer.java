package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
        //使用Flux默认的ArrayList作为buffer
        buffer();
        bufferWithMax();
        bufferWithMaxBiggerThanSkip();
        bufferWithSkipBiggerThanMax();
        bufferWithMaxEqualsSkip();

        //使用用户自定义的Collection作为buffer
        bufferViaCollection();
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

    private static void bufferWithMaxBiggerThanSkip() {
        StepVerifier.create(
                //buffer 为2，skip 为1，元素共5个，返回值下标为：
                //（0,1) (1,2),(2,3),(3,4),(4)
                just.buffer(2, 1)
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b")))
                .expectNext(new ArrayList<>(Arrays.asList("b", "c")))
                .expectNext(new ArrayList<>(Arrays.asList("c", "d")))
                .expectNext(new ArrayList<>(Arrays.asList("d", "e")))
                .expectNext(new ArrayList<>(Arrays.asList("e")))
                .verifyComplete();
    }

    private static void bufferWithSkipBiggerThanMax() {
        StepVerifier.create(
                //buffer 为2，skip 为3，元素共5个，返回值下标为：
                //（0,1) (3,4)
                just.buffer(2, 3)
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b")))
                .expectNext(new ArrayList<>(Arrays.asList("d", "e")))
                .verifyComplete();
    }

    private static void bufferWithMaxEqualsSkip() {
        StepVerifier.create(
                // buffer 为2，skip 为2，元素共5个，返回值下标为：
                //（0,1) (2,3) (4)
                // 即当maxSize等于skip时，等价于只有maxSize
                just.buffer(2, 2)
        ).expectNext(new ArrayList<>(Arrays.asList("a", "b")))
                .expectNext(new ArrayList<>(Arrays.asList("c", "d")))
                .expectNext(new ArrayList<>(Arrays.asList("e")))
                .verifyComplete();
    }

    private static void bufferViaCollection() {
        StepVerifier.create(
                just.buffer(3, () -> new LinkedList<String>())
        ).expectNext(new LinkedList<>(Arrays.asList("a", "b", "c")))
                .expectNext(new LinkedList<>(Arrays.asList("d", "e")))
                .verifyComplete();
    }
}
