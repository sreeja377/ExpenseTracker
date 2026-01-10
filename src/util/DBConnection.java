package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";          // <-- Replace with your MySQL username
    private static final String PASSWORD = "root123";          // <-- Replace with your MySQL password

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            if (con == null) {
                throw new RuntimeException("❌ Database connection failed!");
            }
            return con;
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            return null;
        }
    }
}
