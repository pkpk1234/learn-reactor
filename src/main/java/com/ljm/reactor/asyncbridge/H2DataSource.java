package com.ljm.reactor.asyncbridge;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-05-30
 */
public class H2DataSource {
    JdbcConnectionPool cp = null;

    public H2DataSource() {
        cp = JdbcConnectionPool.
                create("jdbc:h2:~/test", "sa", "sa");
    }

    public void close() {
        this.cp.dispose();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = cp.getConnection();
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        H2DataSource h2DataSource = new H2DataSource();
        Connection connection = h2DataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SHOW TABLES");
        while (result.next()) {
            System.out.println(result.getString(1));
        }
        connection.close();
        h2DataSource.close();
    }
}
