package com.ljm.reactor.setpverifier;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-26
 */
public class ThenConsumeWhile {
    public static void main(String[] args) {
        //素数数据流
        Flux<Integer> primes = Flux.range(1, 100)
                .filter(integer -> isPrime(integer));

        StepVerifier.create(primes)
                //素数肯定无法整除8
                .thenConsumeWhile(integer -> (integer % 8 != 0))
                .verifyComplete();

    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i < n; i += 2) {

            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
