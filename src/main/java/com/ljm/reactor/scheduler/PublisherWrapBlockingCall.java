package com.ljm.reactor.scheduler;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-25
 */
public class PublisherWrapBlockingCall {
    public static void main(String[] args) throws InterruptedException {
        //提前构造出线程池
        Schedulers.elastic();

        String[] files = {
                "com/ljm/reactor/scheduler/PublisherWrapBlockingCall.class",
                "com/ljm/reactor/scheduler/FluxPublishOn.class",
                "com/ljm/reactor/scheduler/FluxPublishOnThreadSwitch.class"
        };
        //同步读取
        Instant start = Instant.now();
        for (String fileName : files) {
            blockingRead(fileName);
        }
        Instant end = Instant.now();
        System.out.println("\n\n>>>>>>>> blocking read cost mills：" + Duration.between(start, end).toMillis());

        //异步读取
        CountDownLatch latch = new CountDownLatch(3);
        start = Instant.now();

        for (String file : files) {
            Mono.fromRunnable(() -> blockingRead(file))
                    //让前面的操作运行在线程池中
                    .subscribeOn(Schedulers.elastic())
                    .doOnTerminate(() -> latch.countDown())
                    .subscribe();
        }
        latch.await();
        end = Instant.now();
        System.out.println("\n\n>>>>>>>>> async read cost mills：" + Duration.between(start, end).toMillis());
    }

    /**
     * 同步读取文件并打印
     * @param fileName
     */
    private static void blockingRead(String fileName) {

        InputStream in = PublisherWrapBlockingCall.class.getClassLoader().getResourceAsStream(fileName);
        try {
            int i = -1;
            while ((i = in.read()) != -1) {
                System.out.print(i);
            }
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
