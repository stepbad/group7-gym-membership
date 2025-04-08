package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.*;
import com.group7.gym.service.*;
import com.group7.gym.utils.PasswordUtils;

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
        MembershipService membershipService = new MembershipService();
        MemberService memberService = new MemberService();
        WorkoutClassService workoutClassService = new WorkoutClassService(scanner);

        System.out.println("Welcome to Gym Management System!");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

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
                } catch (Exception e) {
                    System.err.println("Error checking email: " + e.getMessage());
                    continue;
                }

                String hashedPassword = password;

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

                User user = userService.loginUser(email, password);
                if (user != null) {
                    System.out.println("Login Successful! Welcome, " + user.getUsername());
                    switch (user.getRole().toLowerCase()) {
                        case "admin":
                            new AdminMenu(scanner, new AdminService(), membershipService, user).show();
                            break;
                        case "trainer":
                            new TrainerMenu(scanner, new TrainerService(workoutClassService), workoutClassService, user).show();
                            break;
                        case "member":
                            new MemberMenu(scanner, memberService, workoutClassService, membershipService, user).show();
                            break;
                        default:
                            System.out.println("Unknown role.");
                    }
                } else {
                    System.out.println("Invalid email or password. Try again.");
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
