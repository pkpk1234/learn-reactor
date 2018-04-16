package com.ljm.reactor;

import lombok.AllArgsConstructor;

import java.util.function.Supplier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
@AllArgsConstructor
public class DivideIntegerSupplier implements Supplier<Integer> {
    private Integer integer1;
    private Integer integer2;

    @Override
    public Integer get() {
        return integer1 / integer2;
    }
}
