package com.group7.gym;

import com.group7.gym.models.Trainer;
import com.group7.gym.models.User;
import com.group7.gym.models.WorkoutClass;
import com.group7.gym.service.TrainerService;
import com.group7.gym.service.WorkoutClassService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TrainerMenu {
    private final Scanner scanner;
    private final TrainerService trainerService;
    private final WorkoutClassService workoutClassService;
    private final User trainer;

    public TrainerMenu(Scanner scanner, TrainerService trainerService, WorkoutClassService workoutClassService, User trainer) {
        this.scanner = scanner;
        this.trainerService = trainerService;
        this.workoutClassService = workoutClassService;
        this.trainer = trainer;
    }

    public void show() {
        while (true) {
            System.out.println("\n========== Trainer Menu ==========");
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

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("----- Trainer Profile -----");
                    Trainer t = trainerService.getTrainerById(trainer.getUserId());
                    if (t != null) {
                        System.out.println("\nYour Profile:");
                        System.out.println("ID       : " + t.getUserId());
                        System.out.println("Username : " + t.getUsername());
                        System.out.println("Email    : " + t.getEmail());
                        System.out.println("Phone    : " + t.getPhone());
                        System.out.println("Address  : " + t.getAddress());
                    } else {
                        System.out.println("Trainer profile not found.");
                    }
                    break;
                case 2:
                    trainerService.viewAssignedClasses(trainer.getUserId());
                    break;
                case 3:
                    workoutClassService.listAllWorkoutClasses();
                    break;
                case 4:
                    System.out.print("Enter class ID to assign: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid class ID.");
                        scanner.nextLine();
                        break;
                    }
                    int assignId = scanner.nextInt();
                    scanner.nextLine();
                    trainerService.assignToClass(assignId, trainer.getUserId());
                    break;
                case 5:
                    System.out.print("Enter class ID to unassign: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid class ID.");
                        scanner.nextLine();
                        break;
                    }
                    int unassignId = scanner.nextInt();
                    scanner.nextLine();
                    workoutClassService.unassignTrainerFromClass(unassignId);
                    break;
                case 6:
                    System.out.print("Enter class type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter class description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter class date (YYYY-MM-DD): ");
                    String dateInput = scanner.nextLine();
                    System.out.print("Enter start time (HH:MM, 24hr): ");
                    String startInput = scanner.nextLine();
                    System.out.print("Enter end time (HH:MM, 24hr): ");
                    String endInput = scanner.nextLine();

                    try {
                        WorkoutClass newClass = new WorkoutClass(
                            type,
                            desc,
                            trainer.getUserId(),
                            LocalTime.parse(startInput),
                            LocalTime.parse(endInput),
                            LocalDate.parse(dateInput)
                        );
                        workoutClassService.createWorkoutClass(newClass);
                    } catch (Exception e) {
                        System.err.println("Error: Invalid input format. " + e.getMessage());
                    }
                    break;
                case 7:
                    System.out.print("Enter class ID to update: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid class ID.");
                        scanner.nextLine();
                        break;
                    }
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new type: ");
                    String newType = scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    System.out.print("Enter new class date (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();
                    System.out.print("Enter new start time (HH:MM, 24hr): ");
                    String newStart = scanner.nextLine();
                    System.out.print("Enter new end time (HH:MM, 24hr): ");
                    String newEnd = scanner.nextLine();

                    try {
                        WorkoutClass updated = new WorkoutClass(
                            updateId,
                            newType,
                            newDesc,
                            trainer.getUserId(),
                            LocalTime.parse(newStart),
                            LocalTime.parse(newEnd),
                            LocalDate.parse(newDate)
                        );
                        workoutClassService.updateWorkoutClass(updated);
                    } catch (Exception e) {
                        System.err.println("Error: Invalid input format. " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Enter class ID to delete: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid class ID.");
                        scanner.nextLine();
                        break;
                    }
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    workoutClassService.deleteWorkoutClass(deleteId);
                    break;
                case 9:
                    System.out.print("Enter class ID to view roster: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid class ID.");
                        scanner.nextLine();
                        break;
                    }
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
}
