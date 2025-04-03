package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.User;
import com.group7.gym.service.AdminService;
import com.group7.gym.dao.AdminDAO;
import com.group7.gym.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
                        loadRoleBasedMenu(user, scanner);
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
     * @param scanner Scanner object for input
     */
    private static void loadRoleBasedMenu(User user, Scanner scanner) {
        switch (user.getRole().toLowerCase()) {
            case "admin":
                showAdminMenu(scanner);
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

    /**
     * Displays the admin menu with options.
     *
     * @param scanner Scanner for input
     */
    private static void showAdminMenu(Scanner scanner) {
        AdminService adminService = new AdminService();

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User by ID");
            System.out.println("3. View Memberships & Revenue");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    List<User> users = adminService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        for (User u : users) {
                            System.out.println(u);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter user ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = adminService.deleteUserById(id);
                    System.out.println(deleted ? "User deleted." : "User not found or error occurred.");
                    break;
                case 3:
                    adminService.viewMembershipsAndRevenue();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
