package com.group7.gym;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class WorkoutClassService {

    private WorkoutClassDAO workoutClassDAO;

    public WorkoutClassService(Connection conn) {
        this.workoutClassDAO = new WorkoutClassDAO(conn);
    }

    // CREATE
    public void createWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.addWorkoutClass(workoutClass);
            System.out.println("Workout class successfully created.");
        } catch (SQLException e) {
            System.err.println("Error creating workout class: " + e.getMessage());
        }
    }

    // READ ALL
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

    // READ ONE
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

    // UPDATE
    public void updateWorkoutClass(WorkoutClass updatedClass) {
        try {
            workoutClassDAO.updateWorkoutClass(updatedClass);
            System.out.println("Workout class successfully updated.");
        } catch (SQLException e) {
            System.err.println("Error updating workout class: " + e.getMessage());
        }
    }

    // DELETE
    public void deleteWorkoutClass(int classId) {
        try {
            workoutClassDAO.deleteWorkoutClass(classId);
            System.out.println("Workout class deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting workout class: " + e.getMessage());
        }
    }
}
