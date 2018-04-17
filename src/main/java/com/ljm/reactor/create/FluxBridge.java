package com.ljm.reactor.create;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class FluxBridge {

    private static MyEventProcessor myEventProcessor = new MyEventProcessor() {

        private MyEventListener<String> eventListener;
        private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        @Override
        public void register(MyEventListener<String> eventListener) {
            this.eventListener = eventListener;
        }

        @Override
        public void dataChunk(String... values) {
            executor.schedule(() -> eventListener.onDataChunk(Arrays.asList(values)),
                    500, TimeUnit.MILLISECONDS);
        }

        @Override
        public void processComplete() {
            executor.schedule(() -> eventListener.processComplete(),
                    500, TimeUnit.MILLISECONDS);
        }

        @Override
        public void shutdown() {
            this.executor.shutdownNow();
        }
    };

    public static void main(String[] args) {
        Flux.create(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
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
        }).log().subscribe(System.out::println);
        myEventProcessor.dataChunk("abc", "efg", "123", "456", "end");
    }
}

interface MyEventListener<T> {
    void onDataChunk(List<T> chunk);

    void processComplete();
}

interface MyEventProcessor {
    void register(MyEventListener<String> eventListener);

    void dataChunk(String... values);

    void processComplete();

    void shutdown();
}

