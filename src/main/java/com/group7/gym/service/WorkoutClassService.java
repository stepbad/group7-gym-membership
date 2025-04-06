package com.group7.gym.service;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.dao.WorkoutClassDAO;
import com.group7.gym.models.WorkoutClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class for handling business logic related to workout classes.
 */
public class WorkoutClassService {

    private WorkoutClassDAO workoutClassDAO;

    /**
     * Constructs a WorkoutClassService and initializes the DAO.
     */
    public WorkoutClassService() {
        this.workoutClassDAO = new WorkoutClassDAO();
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
     * Lists all workout classes.
     */
    public void listAllWorkoutClasses() {
        try {
            List<WorkoutClass> classList = workoutClassDAO.getAllWorkoutClasses();
            if (classList.isEmpty()) {
                System.out.println("No workout classes found.");
            } else {
                for (WorkoutClass wc : classList) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listing workout classes: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays details for a workout class.
     *
     * @param classId ID of the class to retrieve
     */
    public void getWorkoutClassDetails(int classId) {
        try {
            WorkoutClass wc = workoutClassDAO.getWorkoutClassById(classId);
            if (wc != null) {
                System.out.println("Workout Class Details:");
                System.out.println(wc);
            } else {
                System.out.println("Workout class not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving workout class: " + e.getMessage());
        }
    }

    /**
 * Unassigns a trainer from a workout class by setting trainer_id to NULL.
 *
 * @param classId ID of the workout class to update
 * @return true if successful, false otherwise
 */
public boolean unassignTrainerFromClass(int classId) {
    String sql = "UPDATE workout_classes SET trainer_id = NULL WHERE class_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, classId);
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
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
}