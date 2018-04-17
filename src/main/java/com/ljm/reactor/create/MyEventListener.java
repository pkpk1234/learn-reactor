package com.ljm.reactor.create;

import java.util.List;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-17
 */
interface MyEventListener<T> {
    void onEvents(List<T> chunk);

    void processComplete();
}
