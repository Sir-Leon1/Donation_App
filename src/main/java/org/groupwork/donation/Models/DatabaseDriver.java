package org.groupwork.donation.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {
    private static final String JDBC_URL = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11693731";
    private static final String USERNAME = "sql11693731";
    private static final String PASSWORD = "fzBx8RdtCU";
    private static Connection connection;

    public DatabaseDriver() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("DB Connection established");
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

   public Connection connect() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error occurred in connect method: "+e.getMessage());
        }
        return connect;
    }

    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            System.out.println("Error occurred in closing conn: " + e);
            e.printStackTrace();
        }

    }
}
