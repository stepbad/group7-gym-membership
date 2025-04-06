package com.group7.gym;

import com.group7.gym.models.Admin;
import com.group7.gym.models.User;
import com.group7.gym.service.AdminService;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;
    private final AdminService adminService;
    private final User admin;

    public AdminMenu(Scanner scanner, AdminService adminService, User admin) {
        this.scanner = scanner;
        this.adminService = adminService;
        this.admin = admin;
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Admins");
            System.out.println("2. Delete Admin by ID");
            System.out.println("3. View Memberships & Revenue (coming soon)");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Admin> admins = adminService.getAllAdmins();
                    if (admins.isEmpty()) {
                        System.out.println("No admins found.");
                    } else {
                        for (Admin a : admins) {
                            System.out.println(a);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter admin ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = adminService.deleteAdmin(id);
                    System.out.println(deleted ? "Admin deleted." : "Admin not found or error occurred.");
                    break;
                case 3:
                    System.out.println("Feature coming soon: View Memberships & Revenue");
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
