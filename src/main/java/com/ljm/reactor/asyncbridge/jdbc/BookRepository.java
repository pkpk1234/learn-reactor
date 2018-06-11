package com.ljm.reactor.asyncbridge.jdbc;

import reactor.util.function.Tuple2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-06-11
 */
public class BookRepository {

    private static final String SELECTALLBOOKS = "SELECT id ,title,author_id FROM BOOK";

    public List<Book> getAllBooks() {
        List<Book> result = new ArrayList<>(10);
        Connection connection = null;
        try {
            connection = H2DataSource.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet allBooks = statement.executeQuery(SELECTALLBOOKS);
            while (allBooks.next()) {
                int id = allBooks.getInt(1);
                String title = allBooks.getString(2);
                int author_id = allBooks.getInt(3);
                result.add(new Book(id, title, author_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
