package com.ljm.reactor.asyncbridge.jdbc;

import org.apache.commons.lang3.time.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-11
 */
public class BookPageService {

    private static Consumer<Book> bookConsumer = book -> System.out.println("\t" + book);
    private static Consumer<Author> authorConsumer = author -> System.out.println("\t" + author);

    public static void main(String[] args) throws InterruptedException {
        //初始化数据
        H2DataSource.getInstance();
        initScheduler();
        getPage();
        getPageAsync();
    }

    private static void getPage() {
        System.out.println("----------------start get page----------------");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        getAuthors();
        getBooks(bookConsumer);
        stopWatch.stop();
        System.out.println("getPage costs " + stopWatch.getTime() + " mills");
    }

    private static void getAuthors() {
        AuthorRepository authorRepository = new AuthorRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        authorRepository.getAllAuthors().stream().forEach(authorConsumer);
        stopWatch.stop();
        System.out.println("\tgetAuthors costs " + stopWatch.getTime() + " mills");
    }

    private static void getBooks(Consumer<Book> consumer) {

        BookRepository bookRepository = new BookRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        bookRepository.getAllBooks().stream().forEach(bookConsumer);
        stopWatch.stop();
        System.out.println("\tgetBooks costs " + stopWatch.getTime() + " mills");
    }

    private static void getPageAsync() throws InterruptedException {
        System.out.println("----------------start get page async----------------");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AuthorAsyncRepository authorAsyncRepository = new AuthorAsyncRepository();
        Flux<Author> authorFlux = authorAsyncRepository
                .getAllAuthorsAsync().doOnComplete(() -> countDownLatch.countDown());
        authorFlux.subscribe(authorConsumer);

        BookAsyncRepository bookAsyncRepository = new BookAsyncRepository();
        Flux<Book> flux = bookAsyncRepository
                .getAllBooksAsync().doOnComplete(() -> countDownLatch.countDown());
        flux.subscribe(bookConsumer);
        //等待异步方法都完成
        countDownLatch.await();
        stopWatch.stop();
        System.out.println("getPage costs " + stopWatch.getTime() + " mills");
    }

    private static void getAuthosAsync() {
        initScheduler();
        AuthorAsyncRepository authorAsyncRepository = new AuthorAsyncRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux<Author> flux = authorAsyncRepository
                .getAllAuthorsAsync();
        flux.subscribe(authorConsumer);
        System.out.println("\tAsync call started");
        flux.blockLast();
        stopWatch.stop();
        System.out.println("\tgetAuthosAsync costs " + stopWatch.getTime() + " mills");
    }

    private static void getBooksAsync(Consumer<Book> consumer) {
        initScheduler();
        BookAsyncRepository bookAsyncRepository = new BookAsyncRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux<Book> flux = bookAsyncRepository
                .getAllBooksAsync();
        flux.subscribe(bookConsumer);
        System.out.println("\tAsync call started");
        flux.blockLast();
        stopWatch.stop();
        System.out.println("\tgetBooksAsync costs " + stopWatch.getTime() + " mills");
    }

    /**
     * Flux的parallel Scheduler初始化较慢，单独出来，在使用之前初始化
     * parallel单例的，所以此处初始化之后，后面直接用
     */
    private static void initScheduler() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Flux<String> stringFlux = Flux.just("1").subscribeOn(Schedulers.parallel());
        stringFlux.subscribe();
        stringFlux.blockLast();
        stopWatch.stop();
        System.out.println("initScheduler costs " + stopWatch.getTime() + " mills");
    }


}
