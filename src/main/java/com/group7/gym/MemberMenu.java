package com.group7.gym;

import com.group7.gym.models.Membership;
import com.group7.gym.models.User;
import com.group7.gym.service.MemberService;
import com.group7.gym.service.MembershipService;
import com.group7.gym.service.WorkoutClassService;

import java.time.LocalDate;
import java.util.List;
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
        } else {
            double total = 0;
            System.out.println("\nYour Memberships:");
            for (Membership membership : memberships) {
                System.out.println(membership);
                total += membership.getMembershipCost();
            }
            System.out.println("Total Expenses: $" + total);
        }
    }

    // Method to purchase a new gym membership
    private void purchaseNewMembership() {
        System.out.print("Enter membership cost: ");
        double cost;
        try {
            cost = Double.parseDouble(scanner.nextLine());  // Parses cost entered by the user
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Cost must be a number.");
            return;
        }

        if (cost <= 0) {
            System.out.println("Cost must be greater than 0.");
            return;
        }

        // Ask for membership type
        System.out.print("Enter membership type (Gold, Silver, Platinum): ");
        String membershipType = scanner.nextLine();

        // Creating and setting up the new membership
        Membership membership = new Membership();
        membership.setMemberId(member.getUserId());  // Set the member ID from the logged-in user
        membership.setMembershipCost(cost);  // Set the membership cost entered by the user
        membership.setMembershipType(membershipType);
        membership.setStartDate(LocalDate.now());  // Set the start date to the current date
        membership.setEndDate(LocalDate.now().plusMonths(1));  // Set the end date to 1 month from now

        // Adding the membership to the system
        boolean success = membershipService.addMembership(membership);
        if (success) {
            System.out.println("New membership purchased successfully!");
        } else {
            System.out.println("Failed to purchase membership.");
        }
    }
}
