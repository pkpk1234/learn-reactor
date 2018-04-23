package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-23
 */
public class FromIterable {
    public static void main(String[] args) {
        SortedMap<String, Charset> charSetMap = Charset.availableCharsets();
        Iterable<String> iterable = charSetMap.keySet();

        Flux<String> charsetFlux = Flux.fromIterable(iterable);
        charsetFlux.subscribe(System.out::println);
    }
}
