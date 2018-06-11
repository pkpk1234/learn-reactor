package com.ljm.reactor.asyncbridge.jdbc;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-12
 */
public class AuthorAsyncRepository {
    private static final String SELECTALLBOOKS = "SELECT id ,name FROM AUTHOR";

    public Flux<Author> getAllAuthorsAsync() {
        Flux<Author> objectFlux = Flux.create(fluxSink -> {
            Connection connection = null;
            try {
                connection = H2DataSource.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet allAthors = statement.executeQuery(SELECTALLBOOKS);
                while (allAthors.next()) {
                    int id = allAthors.getInt(1);
                    String name = allAthors.getString(2);
                    //推送数据
                    fluxSink.next(new Author(id, name));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                //传播异常
                fluxSink.error(e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //终止关闭
                fluxSink.complete();
            }
        });
        return objectFlux.subscribeOn(Schedulers.parallel(), false);
    }
}
