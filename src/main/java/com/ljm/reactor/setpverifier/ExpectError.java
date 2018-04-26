package com.ljm.reactor.setpverifier;

import com.ljm.reactor.DivideIntegerSupplier;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-26
 */
public class ExpectError {
    public static void main(String[] args) {
        Flux<Integer> integerFluxWithException = Flux.just(
                new DivideIntegerSupplier(1, 2),
                new DivideIntegerSupplier(8, 2),
                new DivideIntegerSupplier(20, 10),
                //异常数据,抛出ArithmeticException
                new DivideIntegerSupplier(1, 0),
                new DivideIntegerSupplier(2, 2)
        ).map(divideIntegerSupplier -> divideIntegerSupplier.get());

        StepVerifier.create(integerFluxWithException)
                .expectNext(1 / 2)
                .expectNext(8 / 2)
                .expectNext(20 / 10)
                //校验异常数据，可以判断抛出的异常的类型是否符合预期
                .expectError(ArithmeticException.class)
                .verify();
    }
}
