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

    private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE AUTHOR" +
            "(id INT NOT NULL,name VARCHAR(20) NOT NULL,CONSTRAINT PK_AUTHOR PRIMARY KEY (ID) );";
    private static final String INSERT_TABLE_AUTHOR = "INSERT INTO AUTHOR(id,name) values" +
            "(1,'author1')," +
            "(2,'author2');";

    private static final String CREATE_TABLE_BOOK = "CREATE TABLE BOOK " +
            "(id INT NOT NULL, title VARCHAR(50) " +
            "NOT NULL, author VARCHAR(20) NOT NULL, CONSTRAINT PK_BOOK PRIMARY KEY (ID) );";
    private static final String INSERT_TABLE_BOOK = "INSERT INTO BOOK(id,title,author) VALUES " +
            "(1,'book1','author1')," +
            "(2,'book2','author1')," +
            "(3,'book3','author1')," +
            "(4,'book4','author2')," +
            "(5,'book5','author2')," +
            "(6,'book6','author2')," +
            "(7,'book7','author2')," +
            "(8,'book8','author2')," +
            "(9,'book9','author2');";

    private H2DataSource() {
        cp = JdbcConnectionPool.
                create("jdbc:h2:mem:test", "sa", "sa");
        init();
    }

    private void init() {
        try {
            this.cp.getConnection().createStatement().execute(CREATE_TABLE_BOOK);
            this.cp.getConnection().createStatement().execute(INSERT_TABLE_BOOK);
            this.cp.getConnection().createStatement().execute(CREATE_TABLE_AUTHOR);
            this.cp.getConnection().createStatement().execute(INSERT_TABLE_AUTHOR);
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
        H2DataSource h2DataSource = H2DataSource.getInstance();
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
