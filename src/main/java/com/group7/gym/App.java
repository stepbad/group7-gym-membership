package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.User;
import com.group7.gym.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main entry point for the Gym Management System.
 * Handles user login and routes users to role-based interfaces.
 */
public class App {

    /**
     * Launches the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = DatabaseConnection.getConnection();
        UserDAO userDAO = new UserDAO();

        System.out.println("Welcome to Gym Management System!");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                try {
                    User user = userDAO.getUserByEmail(email);
                    if (user != null && PasswordUtils.checkPassword(password, user.getPasswordHash())) {
                        System.out.println("Login Successful! Welcome, " + user.getUsername());
                        loadRoleBasedMenu(user);
                    } else {
                        System.out.println("Invalid email or password. Try again.");
                    }
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                }
            } else if (choice == 2) {
                System.out.println("Exiting system...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    /**
     * Displays a role-specific menu after successful login.
     *
     * @param user The logged-in user
     */
    private static void loadRoleBasedMenu(User user) {
        System.out.println("\nLoading " + user.getRole() + " menu...");
        switch (user.getRole().toLowerCase()) {
            case "admin":
                System.out.println("Admin options: Manage Users, View Reports, etc.");
                break;
            case "trainer":
                System.out.println("Trainer options: Manage Workout Classes, View Members, etc.");
                break;
            case "member":
                System.out.println("Member options: View Classes, Book Sessions, etc.");
                break;
            default:
                System.out.println("Unknown role. Contact support.");
        }
    }
}
