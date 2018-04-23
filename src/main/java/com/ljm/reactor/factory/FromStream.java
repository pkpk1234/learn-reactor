package com.ljm.reactor.factory;

import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.util.SortedMap;
import java.util.stream.Stream;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-23
 */
public class FromStream {
    public static void main(String[] args) {
        SortedMap<String, Charset> charSetMap = Charset.availableCharsets();
        Stream<String> charSetStream = charSetMap.keySet().stream();
        Flux<String> charsetFlux = Flux.fromStream(charSetStream);
        charsetFlux.subscribe(System.out::println);
    }
}
