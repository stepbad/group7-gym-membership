package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.User;
import com.group7.gym.models.Admin;
import com.group7.gym.service.AdminService;
import com.group7.gym.service.TrainerService;
import com.group7.gym.service.MemberService;
import com.group7.gym.service.MembershipService;
import com.group7.gym.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Gym Management System.
 * Handles user login and routes users to role-based interfaces.
 */
public class App {

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

    private static void loadRoleBasedMenu(User user, Scanner scanner) {
        switch (user.getRole().toLowerCase()) {
            case "admin":
                showAdminMenu(scanner);
                break;
            case "trainer":
                showTrainerMenu(user, scanner);
                break;
            case "member":
                showMemberMenu(user, scanner);
                break;
            default:
                System.out.println("Unknown role. Contact support.");
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        AdminService adminService = new AdminService();

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

    private static void showTrainerMenu(User trainer, Scanner scanner) {
        TrainerService trainerService = new TrainerService();

        while (true) {
            System.out.println("\n--- Trainer Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Classes");
            System.out.println("3. Assign Class to Myself");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    trainerService.viewTrainer(trainer.getUserId());
                    break;
                case 2:
                    trainerService.viewAssignedClasses(trainer.getUserId());
                    break;
                case 3:
                    System.out.print("Enter class ID to assign: ");
                    int classId = scanner.nextInt();
                    scanner.nextLine();
                    trainerService.assignToClass(classId, trainer.getUserId());
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMemberMenu(User member, Scanner scanner) {
        MemberService memberService = new MemberService();
        MembershipService membershipService = new MembershipService();

        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Membership");
            System.out.println("3. View Available Classes (coming soon)");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    memberService.viewMember(member.getUserId());
                    break;
                case 2:
                    membershipService.getAllMemberships().stream()
                        .filter(m -> m.getMemberId() == member.getUserId())
                        .findFirst()
                        .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("No membership found for this member.")
                        );
                    break;
                case 3:
                    System.out.println("Feature coming soon: Browse Available Classes");
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
