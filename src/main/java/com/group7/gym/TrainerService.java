package com.group7.gym;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TrainerService {
    private TrainerDAO trainerDAO;
    private WorkoutClassDAO workoutClassDAO;

    public TrainerService(Connection conn) {
        this.trainerDAO = new TrainerDAO(conn);
        this.workoutClassDAO = new WorkoutClassDAO(conn);
    }

    public void viewAssignedClasses(int trainerId) {
        try {
            List<WorkoutClass> classes = trainerDAO.getAssignedClasses(trainerId);
            if (classes.isEmpty()) {
                System.out.println("You are not assigned to any workout classes.");
            } else {
                System.out.println("Your Assigned Classes:");
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving assigned classes: " + e.getMessage());
        }
    }

    public void createWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.addWorkoutClass(workoutClass);
            System.out.println("Workout class created.");
        } catch (SQLException e) {
            System.err.println("Error creating workout class: " + e.getMessage());
        }
    }

    public void deleteWorkoutClass(int classId) {
        try {
            workoutClassDAO.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting workout class: " + e.getMessage());
        }
    }

    public void assignTrainerToExistingClass(int classId, int trainerId) {
        try {
            trainerDAO.assignTrainerToClass(classId, trainerId);
            System.out.println("Trainer assigned to class.");
        } catch (SQLException e) {
            System.err.println("Error assigning trainer to class: " + e.getMessage());
        }
    }
}
