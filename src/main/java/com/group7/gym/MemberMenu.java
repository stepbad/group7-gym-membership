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

    /**
     * Constructs a MemberMenu with the necessary services and the logged-in user.
     */
    public MemberMenu(Scanner scanner, MemberService memberService, WorkoutClassService workoutClassService, MembershipService membershipService, User member) {
        this.scanner = scanner;
        this.memberService = memberService;
        this.workoutClassService = workoutClassService;
        this.membershipService = membershipService;
        this.member = member;
    }

    /**
     * Displays the main menu for the member and handles user input.
     */
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
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (member instanceof com.group7.gym.models.Member) {
                        workoutClassService.browseWorkoutClasses((com.group7.gym.models.Member) member);
                    } else {
                        System.out.println("Error: This option is only available for members.");
                    }
                    break;
                case 2:
                    viewMembershipExpenses();
                    break;
                case 3:
                    purchaseNewMembership();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Displays all memberships associated with the member along with their status and cost.
     */
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

    /**
     * Determines whether a given membership is currently active or expired.
     */
    private String calculateMembershipStatus(Membership membership) {
        if (membership.getEndDate() == null) {
            return "Active";
        }
        return LocalDate.now().isAfter(membership.getEndDate()) ? "Expired" : "Active";
    }

    /**
     * Allows the user to purchase a new membership, selecting from predefined options.
     * Each membership type has a cost, description, and may include credits.
     */
    private void purchaseNewMembership() {
        while (true) {
            System.out.println("--- Available Membership Options ---");
            System.out.println("1. Platinum ($180): All Gold benefits + VIP lounge, personal locker. [30 credits]");
            System.out.println("2. Gold ($100): Access to all classes, priority booking, free trainer sessions. [15 credits]");
            System.out.println("3. Silver ($40): Access to GYM facilities. Purchase credit $5. [0 credits]");
            System.out.println("4. Daily Basic ($8): One day access to gym facilities. [0 credits]");
            System.out.println("5. Daily Premium ($30): One day access to VIP and all Platinum benefits. [0 credits]");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            String membershipType = "";
            double cost = 0.0;
            String description = "";
            int credits = 0;

            switch (choice) {
                case 1:
                    membershipType = "Platinum";
                    cost = 180.0;
                    description = "All Gold benefits + VIP lounge, personal locker, up to 6 free trainer sessions per month.";
                    credits = 30;
                    break;
                case 2:
                    membershipType = "Gold";
                    cost = 100.0;
                    description = "Access to Pool, Sauna, priority booking for classes, 2 free trainer sessions per month.";
                    credits = 15;
                    break;
                case 3:
                    membershipType = "Silver";
                    cost = 40.0;
                    description = "Access to most classes, standard booking.";
                    credits = 0;
                    break;
                case 4:
                    membershipType = "Daily Basic";
                    cost = 8.0;
                    description = "One day access to gym facilities.";
                    credits = 0;
                    break;
                case 5:
                    membershipType = "Daily Premium";
                    cost = 30.0;
                    description = "One day access to VIP and all Platinum benefits.";
                    credits = 0;
                    break;
                case 6:
                    System.out.println("Exiting membership purchase...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select between 1-5.");
                    continue;
            }

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = membershipType.equals("Daily") ? startDate : startDate.plusMonths(1);

            Membership newMembership = new Membership(0, membershipType, description, cost, member.getUserId(), startDate, endDate, credits);
            membershipService.addMembership(newMembership);

            System.out.println("Membership purchased successfully: " + membershipType + " for $" + cost);
            break;
        }
    }
}
