package com.example.demojavafx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {


    // JDBC URL, username, and password of PostgreSQL server
    private static final String URL = "jdbc:postgresql://localhost:5432/ToDo_List";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "panhadb";

    // Prevent instantiation
    private DBUtil() {}

    public static Connection createConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        connection.setAutoCommit(false);
        return connection;
    }

    /*public static Connection createConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/java_scholarship_db",
                "postgres",
                "panhadb");

        connection.setAutoCommit(false);
        return connection;
    }*/
}
