package com.group7.gym.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.group7.gym.dao.WorkoutClassDAO;
import com.group7.gym.models.WorkoutClass;

/**
 * Service layer for managing workout class operations.
 */
public class WorkoutClassService {

    private WorkoutClassDAO workoutClassDAO;

    /**
     * Constructs the service using a DB connection.
     *
     * @param conn Active DB connection
     */
    public WorkoutClassService(Connection conn) {
        this.workoutClassDAO = new WorkoutClassDAO(conn);
    }

    /**
     * Creates a new workout class.
     *
     * @param workoutClass WorkoutClass to create
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
     * Lists all workout classes in the system.
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
     * Displays details of a workout class by ID.
     *
     * @param classId Class ID to look up
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
     * Updates an existing workout class.
     *
     * @param updatedClass WorkoutClass with new values
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
     * Deletes a workout class by ID.
     *
     * @param classId Class ID to delete
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
