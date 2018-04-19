package com.ljm.reactor.debugging;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-19
 */
public class DebugMode {
    public static void main(String[] args) {
        try {
            Flux.just("0", "1", "2", "abc")
                    .map(i -> Integer.parseInt(i) + "").subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Hooks.onOperatorDebug();
        Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "").subscribe();
    }
}
