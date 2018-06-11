package com.ljm.reactor.asyncbridge.jdbc;

import org.apache.commons.lang3.time.StopWatch;

import java.util.function.Consumer;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-11
 */
public class BookRepositoryCaller {

    private static Consumer<Book> bookConsumer = System.out::println;

    public static void main(String[] args) {
        consumeBooks(bookConsumer);
    }

    private static void consumeBooks(Consumer<Book> consumer) {
        BookRepository bookRepository = new BookRepository();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        bookRepository.getAllBooks().stream().forEach(bookConsumer);

        stopWatch.stop();
        System.out.println("consumeBooks costs " + stopWatch.getTime() + " mills");
    }

}
