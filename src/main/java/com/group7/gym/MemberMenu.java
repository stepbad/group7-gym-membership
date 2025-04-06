package com.group7.gym;

import com.group7.gym.models.User;
import com.group7.gym.service.MemberService;
import com.group7.gym.service.WorkoutClassService;

import java.util.Scanner;

public class MemberMenu {
    private final Scanner scanner;
    private final MemberService memberService;
    private final WorkoutClassService workoutClassService;
    private final User member;

    public MemberMenu(Scanner scanner, MemberService memberService, WorkoutClassService workoutClassService, User member) {
        this.scanner = scanner;
        this.memberService = memberService;
        this.workoutClassService = workoutClassService;
        this.member = member;
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. Browse Workout Classes");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    workoutClassService.listAllWorkoutClasses();
                    break;
                case 2:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
