package com.ljm.reactor;


import java.util.function.Supplier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class DivideIntegerSupplier implements Supplier<Integer> {
    private Integer integer1;
    private Integer integer2;

    public DivideIntegerSupplier(Integer integer1, Integer integer2) {
        this.integer1 = integer1;
        this.integer2 = integer2;
    }

    @Override
    public Integer get() {
        return integer1 / integer2;
    }
}
