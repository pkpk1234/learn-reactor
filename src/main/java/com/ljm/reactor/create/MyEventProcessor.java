package com.ljm.reactor.create;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-17
 */
interface MyEventProcessor {
    void register(MyEventListener<String> eventListener);

    void fireEvents(String... values);

    void processComplete();

    void shutdown();
}
