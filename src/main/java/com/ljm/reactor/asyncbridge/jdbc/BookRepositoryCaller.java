package com.ljm.reactor.asyncbridge.jdbc;

import org.apache.commons.lang3.time.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-11
 */
public class BookRepositoryCaller {

    private static Consumer<Book> bookConsumer = System.out::println;

    public static void main(String[] args) {
        consumeBooks(bookConsumer);
        consumeBooksAsync(bookConsumer);
    }

    private static void consumeBooks(Consumer<Book> consumer) {
        initDataSource();
        BookRepository bookRepository = new BookRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        bookRepository.getAllBooks().stream().forEach(bookConsumer);
        stopWatch.stop();
        System.out.println("consumeBooks costs " + stopWatch.getTime() + " mills");
    }

    private static void consumeBooksAsync(Consumer<Book> consumer) {
        initScheduler();
        BookAsyncRepository bookAsyncRepository = new BookAsyncRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux<Book> flux = bookAsyncRepository
                .getAllBooksAsync();
        flux.subscribe(bookConsumer);
        System.out.println("Async call started");
        flux.blockLast();
        stopWatch.stop();
        System.out.println("consumeBooksAsync costs " + stopWatch.getTime() + " mills");
    }

    private static void initScheduler() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux<String> stringFlux = Flux.just("1").subscribeOn(Schedulers.parallel());
        stringFlux.subscribe();
        stringFlux.blockLast();
        stopWatch.stop();
        System.out.println("initScheduler costs " + stopWatch.getTime() + " mills");
        initDataSource();
    }

    private static void initDataSource() {
        H2DataSource.getInstance();
    }

}
