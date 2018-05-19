package com.ljm.reactor.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-16
 */

public class MyLimitedSubscriber<T> extends BaseSubscriber<T> {
    private long mills;
    private ThreadPoolExecutor threadPool;
    private int maxWaiting;
    private final Random random = new Random();

    public MyLimitedSubscriber(int maxWaiting) {
        this.maxWaiting = maxWaiting;
        this.threadPool = new ThreadPoolExecutor(
                1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(maxWaiting));
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        this.mills = System.currentTimeMillis();
        requestNextDatas();
    }

    @Override
    protected void hookOnComplete() {
        long now = System.currentTimeMillis();
        long time = now - this.mills;
        System.out.println("cost time:" + time / 1000 + " seconds");
        this.threadPool.shutdown();
    }

    @Override
    protected void hookOnNext(T value) {
        this.threadPool.execute(new MyTask(value));
        requestNextDatas();
    }

    private void requestNextDatas() {
        int requestSize = this.maxWaiting - this.threadPool.getQueue().size();
        if (requestSize > 0) {
            System.out.println("Thread Pool can handle,request " + requestSize);
            request(requestSize);
            return;
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestNextDatas();
        }

    }

    class MyTask<T> implements Runnable {
        private T data;

        public MyTask(T data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.ints(100, 500).findFirst().getAsInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("data is " + data);
        }

    }
}
