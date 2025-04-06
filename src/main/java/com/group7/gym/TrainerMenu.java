package com.group7.gym;

import com.group7.gym.models.User;
import com.group7.gym.models.WorkoutClass;
import com.group7.gym.service.TrainerService;
import com.group7.gym.service.WorkoutClassService;

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
                        new WorkoutClass(type, desc, trainer.getUserId()));
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
                        new WorkoutClass(updateId, newType, newDesc, trainer.getUserId()));
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
}
