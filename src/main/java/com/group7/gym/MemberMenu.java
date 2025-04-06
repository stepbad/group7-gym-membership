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
            System.out.println("2. View My Membership Expenses");
            System.out.println("3. Purchase Membership");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    workoutClassService.listAllWorkoutClasses();
                    break;
                case 2:
                    memberService.viewMembershipExpenses(member.getUserId());
                    break;
                case 3:
                    System.out.print("Enter membership type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter membership description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter cost: ");
                    double cost = scanner.nextDouble();
                    scanner.nextLine();
                    memberService.purchaseMembership(member.getUserId(), type, desc, cost);
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
