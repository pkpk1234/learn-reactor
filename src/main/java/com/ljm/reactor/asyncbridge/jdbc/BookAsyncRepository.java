package com.ljm.reactor.asyncbridge.jdbc;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-11
 */
public class BookAsyncRepository {
    private static final String SELECTALLBOOKS = "SELECT id ,title,author FROM BOOK";

    Flux<Book> getAllBooksAsync() {

        Flux<Book> objectFlux = Flux.create(fluxSink -> {
            Connection connection = null;
            try {
                connection = H2DataSource.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet allBooks = statement.executeQuery(SELECTALLBOOKS);
                while (allBooks.next()) {
                    int id = allBooks.getInt(1);
                    String title = allBooks.getString(2);
                    String author = allBooks.getString(3);
                    fluxSink.next(new Book(id, title, author));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                fluxSink.error(e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                fluxSink.complete();
            }
        });
        return objectFlux.subscribeOn(Schedulers.elastic(), false);
    }
}
