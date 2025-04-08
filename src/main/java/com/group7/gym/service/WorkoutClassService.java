package com.group7.gym.service;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.dao.*;
import com.group7.gym.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Service class for handling business logic related to workout classes.
 */
public class WorkoutClassService {

    private WorkoutClassDAO workoutClassDAO;
    private MemberClassDAO memberClassDAO;

    /**
     * Constructs a WorkoutClassService and initializes the DAO.
     */
    public WorkoutClassService() {
        this.workoutClassDAO = new WorkoutClassDAO();
        this.memberClassDAO = new MemberClassDAO();
    }

    /**
     * Lists all workout classes for a trainer without member-specific enrollment details.
     */
    public void listAllWorkoutClassesForTrainer() {
        try {
            List<WorkoutClass> classList = workoutClassDAO.getAllWorkoutClasses();
            if (classList.isEmpty()) {
                System.out.println("No workout classes found.");
            } else {
                System.out.println("\n--- All Workout Classes ---");
                for (WorkoutClass wc : classList) {
                    System.out.println(wc.toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listing workout classes: " + e.getMessage());
        }
    }

    /**
     * Creates a new workout class.
     *
     * @param workoutClass The WorkoutClass object to be added
     */
    public void createWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.addWorkoutClass(workoutClass);
            System.out.println("Workout class successfully created.");
        } catch (SQLException e) {
            System.err.println("Error creating workout class: " + e.getMessage());
        }
    }

    /**
     * Lists all workout classes for a member, including enrollment status.
     * @param member The logged-in member
     */
    public void browseWorkoutClasses(Member member) {
        try {
            MembershipService membershipService = new MembershipService();
            Membership membership = membershipService.getCurrentMembershipByMemberId(member.getUserId());

            System.out.println("\n--- Membership Summary ---");
            System.out.println("Type: " + (membership != null ? membership.getMembershipType() : "None"));
            System.out.println("Credits: " + (membership != null ? membership.getAvailableCredits() : "N/A"));

            // List "My Classes"
            List<WorkoutClass> myClasses = memberClassDAO.getClassesByMemberId(member.getUserId());
            System.out.println("\n--- My Classes ---");
            if (myClasses.isEmpty()) {
                System.out.println("You are not enrolled in any classes.");
            } else {
                for (WorkoutClass wc : myClasses) {
                    System.out.println(wc.toString());
                }
            }

            // List "All Classes"
            List<WorkoutClass> allClasses = workoutClassDAO.getAllWorkoutClasses();
            System.out.println("\n--- All Classes ---");
            if (allClasses.isEmpty()) {
                System.out.println("No workout classes available.");
            } else {
                for (WorkoutClass wc : allClasses) {
                    boolean isEnrolled = memberClassDAO.isMemberEnrolledInClass(member.getUserId(), wc.getWorkoutClassId());
                    System.out.println(wc.toString() + (isEnrolled ? " [Enrolled]" : ""));
                }
            }

            // Prompt to register
            if (!allClasses.isEmpty()) {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.print("\nEnter the Class ID to register (or 0 to return): ");
                    if (scanner.hasNextInt()) {
                        int workoutClassId = scanner.nextInt();
                        scanner.nextLine();
                        if (workoutClassId != 0) {
                            registerForClass(member, workoutClassId);
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error browsing workout classes: " + e.getMessage());
        }
    }

    /**
     * Registers a member for a workout class after checking credit eligibility.
     *
     * @param member The logged-in member
     * @param workoutClassId The ID of the class to register for
     */
    private void registerForClass(Member member, int workoutClassId) {
        try {
            if (memberClassDAO.isMemberEnrolledInClass(member.getUserId(), workoutClassId)) {
                System.out.println("You are already enrolled in Class ID " + workoutClassId + ".");
                return;
            }

            WorkoutClass wc = workoutClassDAO.getWorkoutClassById(workoutClassId);
            if (wc == null) {
                System.out.println("Class ID " + workoutClassId + " does not exist.");
                return;
            }

            MembershipService membershipService = new MembershipService();
            Membership membership = membershipService.getCurrentMembershipByMemberId(member.getUserId());

            if (membership == null) {
                System.out.println("You do not have an active membership.");
                return;
            }

            if (!membership.hasAccessToCredits()) {
                System.out.println("Your membership type does not include class access. Please upgrade.");
                return;
            }

            if (membership.getAvailableCredits() <= 0) {
                System.out.println("Not enough credits. Please purchase more credits.");
                return;
            }

            boolean creditUsed = membershipService.useCredit(member.getUserId());

            if (creditUsed) {
                memberClassDAO.addMemberToClass(member.getUserId(), workoutClassId);
                System.out.println("Successfully registered for Class ID " + workoutClassId + "!");
                System.out.println("1 credit has been deducted. Remaining credits: " + (membership.getAvailableCredits() - 1));
            } else {
                System.out.println("Failed to register. Credit deduction failed.");
            }

        } catch (SQLException e) {
            System.err.println("Error registering for class: " + e.getMessage());
        }
    }
    /**
     * Retrieves the list of members enrolled in a specific workout class.
     *
     * @param classId The ID of the workout class
     * @return List of members enrolled in the class
     */
    public List<Member> getClassRoster(int classId) {
        MemberClassDAO memberClassDAO = new MemberClassDAO();
        return memberClassDAO.getMembersByClassId(classId);
    }
    
    /**
     * Unassigns a trainer from a workout class by setting trainer_id to NULL.
     *
     * @param workoutClassId ID of the workout class to update
     * @return true if successful, false otherwise
     */

    public boolean unassignTrainerFromClass(int workoutClassId) {
        String sql = "UPDATE workout_classes SET trainer_id = NULL WHERE workout_class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, workoutClassId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error unassigning trainer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing workout class.
     *
     * @param updatedClass The updated WorkoutClass object
     */

    public void updateWorkoutClass(WorkoutClass updatedClass) {
        try {
            workoutClassDAO.updateWorkoutClass(updatedClass);
            System.out.println("Workout class successfully updated.");
        } catch (SQLException e) {
            System.err.println("Error updating workout class: " + e.getMessage());
        }
    }

    /**
     * Deletes a workout class by its ID.
     *
     * @param classId ID of the class to delete
     */

    public void deleteWorkoutClass(int classId) {
        try {
            workoutClassDAO.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting workout class: " + e.getMessage());
        }
    }

     /**
     * Displays details of a workout class by its ID.
     *
     * @param classId ID of the class to view details of.
     */

    public void getWorkoutClassDetails(int classId) {
        try {
            WorkoutClass wc = workoutClassDAO.getWorkoutClassById(classId);
            if (wc != null) {
                System.out.println("Workout Class Details:");
                System.out.println(wc.toString());
            } else {
                System.out.println("Workout class not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving workout class: " + e.getMessage());
        }
    }
}