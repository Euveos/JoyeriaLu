package com.example.joyerialu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    private static String HOST= "127.0.0.1";
    private static int PORT = 33006;
    private static String DB_NAME = "joyeria";
    private static String USERNAME = "root";
    private static String PASSWORD = "@ite1827";
    private static Connection connection;

    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
