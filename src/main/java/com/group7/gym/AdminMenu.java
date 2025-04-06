package com.group7.gym;

import com.group7.gym.models.Admin;
import com.group7.gym.models.User;
import com.group7.gym.service.AdminService;
import com.group7.gym.service.MembershipService;

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
            System.out.println("2. View All Admins");
            System.out.println("3. Delete Admin by ID");
            System.out.println("4. View Memberships & Revenue");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("----- Admin Profile -----");
                    Admin a = adminService.getAdminById(admin.getUserId());
                    if (a != null) {
                        System.out.println("\nYour Profile:");
                        System.out.println("ID       : " + a.getUserId());
                        System.out.println("Username : " + a.getUsername());
                        System.out.println("Email    : " + a.getEmail());
                        System.out.println("Phone    : " + a.getPhone());
                    } else {
                        System.out.println("Admin profile not found.");
                    }
                    break;
                case 2:
                    System.out.println("----- View All Admins -----");
                    var admins = adminService.getAllAdmins();
                    if (admins.isEmpty()) {
                        System.out.println("No admins found.");
                    } else {
                        for (Admin admin : admins) {
                            System.out.println(admin);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter admin ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = adminService.deleteAdmin(id);
                    System.out.println(deleted ? "Admin deleted." : "Admin not found or error occurred.");
                    break;
                case 4:
                    // View Memberships & Revenue
                    System.out.println("----- Memberships & Revenue -----");
                    double totalRevenue = membershipService.getTotalRevenue();
                    System.out.println("Total Revenue: $" + totalRevenue);
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
