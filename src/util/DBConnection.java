package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

              // <-- Replace with your MySQL password

    private static final String URL = "jdbc:mysql://localhost:3306/expensetracker";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

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
