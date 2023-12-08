package com.example.demojavafx;

import java.sql.*;

public class TestingDatabase {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/ToDo_List";
        String username = "postgres";
        String password = "panhadb";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery("select * from department");
            while (rs.next()) {
                System.out.println("ID is : " + rs.getInt("id"));
                System.out.println("Name is : " + rs.getString("name"));
            }


        } catch (SQLException ex) {
            System.out.println("Error gettin the sql connection: " + ex.getMessage());

        }
    }
}
