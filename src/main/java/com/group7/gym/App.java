package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.*;
import com.group7.gym.service.*;
import com.group7.gym.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main entry point for the Gym Management System.
 * Handles user registration, login, and routes users to role-based interfaces.
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService();

        System.out.println("Welcome to Gym Management System!");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine().trim().toLowerCase();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine().trim();
                System.out.print("Enter Phone: ");
                String phone = scanner.nextLine().trim();
                System.out.print("Enter Address: ");
                String address = scanner.nextLine().trim();
                System.out.print("Enter Role (admin/trainer/member): ");
                String role = scanner.nextLine().trim().toLowerCase();

                if (!role.equals("admin") && !role.equals("trainer") && !role.equals("member")) {
                    System.out.println("Invalid role. Must be admin, trainer, or member.");
                    continue;
                }

                try {
                    if (userDAO.getUserByEmail(email) != null) {
                        System.out.println("Email is already registered.");
                        continue;
                    }
                } catch (SQLException e) {
                    System.err.println("Error checking email: " + e.getMessage());
                    continue;
                }

                String hashedPassword = PasswordUtils.hashPassword(password);
                User newUser;
                switch (role) {
                    case "admin":
                        newUser = new Admin(0, username, hashedPassword, email, phone, address);
                        break;
                    case "trainer":
                        newUser = new Trainer(0, username, hashedPassword, email, phone, address);
                        break;
                    case "member":
                        newUser = new Member(0, username, hashedPassword, email, phone, address, 0, 0.0);
                        break;
                    default:
                        System.out.println("Unexpected error: invalid role.");
                        continue;
                }

                userService.registerUser(newUser);

            } else if (choice == 2) {
                System.out.print("Enter Email: ");
                String email = scanner.nextLine().trim().toLowerCase();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine().trim();

                try {
                    User user = userDAO.getUserByEmail(email);
                    if (user != null && PasswordUtils.checkPassword(password, user.getPasswordHash())) {
                        System.out.println("Login Successful! Welcome, " + user.getUsername());
                        switch (user.getRole().toLowerCase()) {
                            case "admin":
                                AdminMenu adminMenu = new AdminMenu(scanner, new AdminService(), user);
                                adminMenu.show();
                                break;
                            case "trainer":
                                TrainerMenu trainerMenu = new TrainerMenu(scanner, new TrainerService(), new WorkoutClassService(), user);
                                trainerMenu.show();
                                break;
                            case "member":
                                MemberMenu memberMenu = new MemberMenu(scanner, new MemberService(), new WorkoutClassService(), user);
                                memberMenu.show();
                                break;
                            default:
                                System.out.println("Unknown role.");
                        }
                    } else {
                        System.out.println("Invalid email or password. Try again.");
                    }
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                }

            } else if (choice == 3) {
                System.out.println("Exiting system...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
