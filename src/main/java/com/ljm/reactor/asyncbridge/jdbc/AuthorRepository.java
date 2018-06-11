package com.ljm.reactor.asyncbridge.jdbc;

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
public class AuthorRepository {
    private static final String SELECTALLBOOKS = "SELECT id ,name FROM AUTHOR";

    public List<Author> getAllAuthors() {
        List<Author> result = new ArrayList<>(2);
        Connection connection = null;
        try {
            connection = H2DataSource.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet allAthors = statement.executeQuery(SELECTALLBOOKS);
            while (allAthors.next()) {
                int id = allAthors.getInt(1);
                String name = allAthors.getString(2);
                result.add(new Author(id, name));
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
