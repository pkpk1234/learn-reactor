package com.ljm.reactor.asyncbridge.jdbc;

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
    private static final H2DataSource INSTANCE = new H2DataSource();

    private static final String CREATE_TABLE = "CREATE TABLE BOOK " +
            "(id INT NOT NULL, title VARCHAR(50) " +
            "NOT NULL, author VARCHAR(20) NOT NULL);";
    private static final String INIT_INSERTS = "INSERT INTO BOOK(id,title,author) VALUES " +
            "(1,'book1','author1')," +
            "(2,'book2','author2')," +
            "(3,'book3','author3')," +
            "(4,'book4','author4')," +
            "(5,'book5','author5')," +
            "(6,'book6','author6')," +
            "(7,'book7','author7')," +
            "(8,'book8','author8')," +
            "(9,'book9','author9');";

    private H2DataSource() {
        cp = JdbcConnectionPool.
                create("jdbc:h2:mem:test", "sa", "sa");
        init();
    }

    private void init() {
        try {
            this.cp.getConnection().createStatement().execute(CREATE_TABLE);
            this.cp.getConnection().createStatement().execute(INIT_INSERTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        this.cp.dispose();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = cp.getConnection();
        return connection;
    }

    public static H2DataSource getInstance() {
        return INSTANCE;
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
