package com.ljm.reactor.create;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */
public class FluxBridge {

    private static MyEventProcessor<String> myEventProcessor = new MyEventProcessor<String>() {

        private MyEventListener<String> eventListener;
        private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


        @Override
        public void fireEvents(List<String> values) {
            executor.schedule(() -> eventListener.onEvent(values),
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
                        @Override
                        public void onEvent(List<String> chunk) {
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
        myEventProcessor.fireEvents(Arrays.asList("abc", "efg", "123", "456", "end"));
        System.out.println("main thread exit");
    }
}

interface MyEventListener<T> {
    <T> void onEvent(List<T> event);

    void onProcessComplete();
}

abstract class MyEventProcessor<T> {
    private Set<MyEventListener<T>> listeners = new HashSet<>(10);

    protected void register(MyEventListener<T> eventListener) {
        this.listeners.add(eventListener);
    }

    protected abstract <T> void fireEvents(List<T> values);

    protected void processComplete() {
        listeners.forEach(MyEventListener::onProcessComplete);
    }

    protected abstract void shutdown();
}

