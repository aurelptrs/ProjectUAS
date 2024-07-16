package oop.studentmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3307/aplikasimahasiswa";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
