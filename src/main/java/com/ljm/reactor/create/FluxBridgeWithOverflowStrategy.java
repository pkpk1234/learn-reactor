package com.ljm.reactor.create;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-17
 */
public class FluxBridgeWithOverflowStrategy {
    private static MyEventProcessor myEventProcessor = new ScheduledSingleListenerEventProcessor();

    public static void main(String[] args) {
        Flux.create(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        public void onEvents(List<String> chunk) {
                            for (String s : chunk) {
                                if ("end".equalsIgnoreCase(s)) {
                                    sink.complete();
                                    myEventProcessor.shutdown();
                                } else {
                                    sink.next(s);
                                }

                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
        }, FluxSink.OverflowStrategy.ERROR).log().subscribe(System.out::println);
        myEventProcessor.fireEvents("abc", "efg", "123", "456", "end");
        System.out.println("main thread exit");
    }
}
