package main;

import dao.ExpenseDAO;
import model.Expense;

import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseDAO dao = new ExpenseDAO();

        while (true) {
            int choice = 0;
            do {
                System.out.println("\n===== EXPENSE TRACKER MENU =====");
                System.out.println("1. Add Expense");
                System.out.println("2. View Expenses");
                System.out.println("3. Delete Expense");
                System.out.println("4. Category-wise Report");
                System.out.println("5. Monthly Summary");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > 6) {
                        System.out.println("❌ Invalid choice. Please enter 1-6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input. Enter a number.");
                }
            } while (choice < 1 || choice > 6);

            switch (choice) {
                case 1:
                    Expense expense = new Expense();

                    // Validate Title
                    String title;
                    do {
                        System.out.print("Enter Title: ");
                        title = sc.nextLine().trim();
                        if (title.isEmpty()) {
                            System.out.println("❌ Title cannot be empty.");
                        }
                    } while (title.isEmpty());
                    expense.setTitle(title);

                    // Validate Amount
                    double amount = 0;
                    boolean validAmount = false;
                    do {
                        System.out.print("Enter Amount: ");
                        try {
                            amount = Double.parseDouble(sc.nextLine());
                            if (amount <= 0) {
                                System.out.println("❌ Amount must be > 0.");
                            } else {
                                validAmount = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Invalid number.");
                        }
                    } while (!validAmount);
                    expense.setAmount(amount);

                    // Validate Category
                    String category;
                    do {
                        System.out.print("Enter Category: ");
                        category = sc.nextLine().trim();
                        if (category.isEmpty()) {
                            System.out.println("❌ Category cannot be empty.");
                        }
                    } while (category.isEmpty());
                    expense.setCategory(category);

                    // Set current date
                    expense.setDate(new Date(System.currentTimeMillis()));

                    dao.addExpense(expense);
                    break;

                case 2:
                    dao.viewExpenses();
                    break;

                case 3:
                    System.out.print("Enter Expense ID to delete: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        dao.deleteExpense(id);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid ID. Enter a number.");
                    }
                    break;

                case 4:
                    dao.viewCategoryWiseReport();
                    break;

                case 5:
                    dao.viewMonthlySummary();
                    break;

                case 6:
                    System.out.println("👋 Exiting. Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
