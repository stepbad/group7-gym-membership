package com.group7.gym;

import com.group7.gym.models.User;
import com.group7.gym.service.AdminService;
import com.group7.gym.service.MembershipService;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;
    private final AdminService adminService;
    private final MembershipService membershipService;
    private final User admin;

    public AdminMenu(Scanner scanner, AdminService adminService, MembershipService membershipService, User admin) {
        this.scanner = scanner;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.admin = admin;
    }

    public void show() {
        while (true) {
            System.out.println("\n========== Admin Menu ==========");
            System.out.println("1. View My Profile");
            System.out.println("2. View All Users");
            System.out.println("3. Delete User by ID");
            System.out.println("4. View Memberships & Revenue");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("----- Admin Profile -----");
                    User a = admin; // Already logged in and passed in
                    System.out.println("\nYour Profile:");
                    System.out.println("ID       : " + a.getUserId());
                    System.out.println("Username : " + a.getUsername());
                    System.out.println("Email    : " + a.getEmail());
                    System.out.println("Phone    : " + a.getPhone());
                    break;
                case 2:
                    System.out.println("----- View All Users -----");
                    List<User> users = adminService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        for (User user : users) {
                            System.out.println(user);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter user ID to delete: ");
                    try {
                        int userId = Integer.parseInt(scanner.nextLine());
                        boolean deletedUser = adminService.deleteUser(userId);
                        System.out.println(deletedUser ? "User deleted." : "User not found or error occurred.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                    }
                    break;
                case 4:
                    System.out.println("----- Memberships & Revenue -----");
                    double totalRevenue = membershipService.getTotalRevenue();
                    System.out.printf("Total Revenue: $%.2f\n", totalRevenue);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
