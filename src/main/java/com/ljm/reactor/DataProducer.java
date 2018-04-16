package com.ljm.reactor;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
@FunctionalInterface
public interface DataProducer<T> {
    T produce();
}
