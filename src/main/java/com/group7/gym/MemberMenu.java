package com.group7.gym;

import com.group7.gym.models.Membership;
import com.group7.gym.models.User;
import com.group7.gym.service.MemberService;
import com.group7.gym.service.MembershipService;
import com.group7.gym.service.WorkoutClassService;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberMenu {
    private final Scanner scanner;
    private final MemberService memberService;
    private final WorkoutClassService workoutClassService;
    private final MembershipService membershipService;
    private final User member;




    // Constructor that takes the necessary services and user
    public MemberMenu(Scanner scanner, MemberService memberService, WorkoutClassService workoutClassService, MembershipService membershipService, User member) {
        this.scanner = scanner;
        this.memberService = memberService;
        this.workoutClassService = workoutClassService;
        this.membershipService = membershipService;
        this.member = member;
    }

    // Method to display the member menu and handle the actions
    public void show() {
        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. Browse Workout Classes");
            System.out.println("2. View Membership Expenses");
            System.out.println("3. Purchase New Gym Membership");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());  // Ensures that user input is correctly parsed as an integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    workoutClassService.listAllWorkoutClasses();
                    break;
                case 2:
                    viewMembershipExpenses();
                    break;
                case 3:
                    purchaseNewMembership();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;  // Exit the loop and log out
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to view membership expenses
    private void viewMembershipExpenses() {
        List<Membership> memberships = membershipService.getMembershipsByMemberId(member.getUserId());

        if (memberships.isEmpty()) {
            System.out.println("No memberships found for your account.");
            return;
        }

        double activeTotal = 0.0;

        System.out.println("\nYour Memberships:");
        System.out.printf("%-20s%-10s%-10s%n", "Membership Type", "Cost", "Status");
        System.out.println("----------------------------------------------");

        for (Membership membership : memberships) {
            String status = calculateMembershipStatus(membership);

            System.out.printf("%-20s$%-10.2f%-10s%n", membership.getMembershipType(), membership.getMembershipCost(), status);

            if ("Active".equals(status)) {
                activeTotal += membership.getMembershipCost();
            }
        }

        System.out.println("\n--- Membership Report ---");
        System.out.printf("Total Active Memberships Cost: $%.2f%n", activeTotal);
    }

    // Helper method to calculate membership status
    private String calculateMembershipStatus(Membership membership) {
        if (membership.getEndDate() == null) {
            return "Active"; // If no end date, assume active
        }
        return LocalDate.now().isAfter(membership.getEndDate()) ? "Expired" : "Active";
    }



    // Method to purchase a new gym membership
    private void purchaseNewMembership() {
        while (true) {
            System.out.println("\n--- Available Membership Options ---");
            System.out.println("1. P - Platinum ($80): All Gold benefits + VIP lounge, personal locker.");
            System.out.println("2. G - Gold ($50): Access to all classes, priority booking, free trainer sessions.");
            System.out.println("3. S - Silver ($30): Access to most classes, standard booking.");
            System.out.println("4. D - Daily ($5): One day access to gym facilities.");
            System.out.println("5. Exit ");
            System.out.print("\nChoose an option (1-5): ");


            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());  // Ensures that user input is correctly parsed as an integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            String membershipType = "";
            double cost = 0.0;
            String description = "";


            switch (choice) {
                case 1:
                    membershipType = "Platinum";
                    cost = 80.0;
                    description = "All Gold benefits + VIP lounge, personal locker.";
                    break;
                case 2:
                    membershipType = "Gold";
                    cost = 50.0;
                    description = "Access to all classes, priority booking, free trainer sessions.";
                    break;
                case 3:
                    membershipType = "Silver";
                    cost = 30.0;
                    description = "Access to most classes, standard booking.";
                    break;
                case 4:
                    membershipType = "Daily";
                    cost = 5.0;
                    description = "One day access to gym facilities.";
                    break;
                case 5:
                    System.out.println("Exiting membership purchase...");
                    return; // Exit the purchase method
                default:
                    System.out.println("Invalid choice. Please select between 1-5.");
                    continue;
            }

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = membershipType.equals("Daily") ? startDate : startDate.plusMonths(1);
            String status = "Active";

            Membership newMembership = new Membership(0, membershipType, description, cost, member.getUserId(), startDate, endDate);
            membershipService.addMembership(newMembership);

            System.out.println("Membership purchased successfully: " + membershipType + " for $" + cost);
            break; // After purchase, exit the loop

        }

    }
}
