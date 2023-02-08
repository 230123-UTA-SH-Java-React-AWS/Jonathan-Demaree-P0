package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static Connection conn = null;

    private ConnectionUtil() {
        conn = null;
    }

    public static Connection getConnection()
    {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url = "jdbc:postgresql://";
        url += System.getenv("url");
        url += "/";
        String user = System.getenv("user");
        String pass = System.getenv("pass");

        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to database with credentials supplied.");
        }

         return conn;
    }
}