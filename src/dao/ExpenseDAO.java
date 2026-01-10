package dao;

import model.Expense;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseDAO {

    // Add Expense
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (title, category, amount, expense_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, expense.getTitle());
            ps.setString(2, expense.getCategory());
            ps.setDouble(3, expense.getAmount());
            ps.setDate(4, expense.getDate());

            ps.executeUpdate();
            System.out.println("✅ Expense added successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    // View Expenses
    public void viewExpenses() {
        String sql = "SELECT id, title, category, amount, expense_date FROM expenses";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | Title | Category | Amount | Date");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("category") + " | " +
                                rs.getDouble("amount") + " | " +
                                rs.getDate("expense_date")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    // Delete Expense
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("🗑️ Expense deleted successfully!");
            } else {
                System.out.println("❌ No expense found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    // Category-wise Report
    public void viewCategoryWiseReport() {
        String sql = "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n📊 CATEGORY-WISE EXPENSE REPORT");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getString("category") + " → ₹" +
                                rs.getDouble("total")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    // Monthly Summary
    public void viewMonthlySummary() {
        String sql = """
                SELECT DATE_FORMAT(expense_date, '%Y-%m') AS month,
                       SUM(amount) AS total
                FROM expenses
                GROUP BY month
                ORDER BY month
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n📅 MONTHLY EXPENSE SUMMARY");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getString("month") + " → ₹" +
                                rs.getDouble("total")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }
}
