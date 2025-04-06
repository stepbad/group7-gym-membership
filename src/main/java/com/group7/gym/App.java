package com.group7.gym;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.*;
import com.group7.gym.service.*;
import com.group7.gym.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;
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
                switch (role.toLowerCase()) {
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
                        loadRoleBasedMenu(user, scanner);
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
        WorkoutClassService workoutClassService = new WorkoutClassService();

        while (true) {
            System.out.println("\n--- Trainer Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Classes");
            System.out.println("3. View All Classes");
            System.out.println("4. Assign Existing Class");
            System.out.println("5. Unassign Class");
            System.out.println("6. Create New Class");
            System.out.println("7. Update My Class");
            System.out.println("8. Delete My Class");
            System.out.println("9. View Roster for a Class");
            System.out.println("10. Logout");
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
                    workoutClassService.listAllWorkoutClasses();
                    break;
                case 4:
                    System.out.print("Enter class ID to assign: ");
                    int assignId = scanner.nextInt();
                    scanner.nextLine();
                    trainerService.assignToClass(assignId, trainer.getUserId());
                    break;
                case 5:
                    System.out.print("Enter class ID to unassign: ");
                    int unassignId = scanner.nextInt();
                    scanner.nextLine();
                    workoutClassService.unassignTrainerFromClass(unassignId);
                    break;
                case 6:
                    System.out.print("Enter class type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter class description: ");
                    String desc = scanner.nextLine();
                    workoutClassService.createWorkoutClass(
                        new com.group7.gym.models.WorkoutClass(type, desc, trainer.getUserId()));
                    break;
                case 7:
                    System.out.print("Enter class ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new type: ");
                    String newType = scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    workoutClassService.updateWorkoutClass(
                        new com.group7.gym.models.WorkoutClass(updateId, newType, newDesc, trainer.getUserId()));
                    break;
                case 8:
                    System.out.print("Enter class ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    workoutClassService.deleteWorkoutClass(deleteId);
                    break;
                case 9:
                    System.out.print("Enter class ID to view roster: ");
                    int classId = scanner.nextInt();
                    scanner.nextLine();
                    trainerService.viewRoster(classId);
                    break;
                
                case 10:
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
        WorkoutClassService workoutClassService = new WorkoutClassService();

        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Membership");
            System.out.println("3. View Available Classes");
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
                    workoutClassService.listAllWorkoutClasses();
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
