package com.group7.gym.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.dao.*;
import com.group7.gym.models.*;

/**
 * Service class for trainer-related business logic.
 */
public class TrainerService {
    private TrainerDAO trainerDAO;
    private final WorkoutClassService workoutClassService;

    /**
     * Default constructor initializes TrainerDAO with internal connection.
     * Assumes WorkoutClassService with no scanner (for backward compatibility).
     */
    public TrainerService(WorkoutClassService workoutClassService) {
        this.trainerDAO = new TrainerDAO();
        this.workoutClassService = workoutClassService;
    }

    public void registerTrainer(Trainer trainer) {
        try {
            trainerDAO.addTrainer(trainer);
            System.out.println("Trainer registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering trainer: " + e.getMessage());
        }
    }

    public void listAllTrainers() {
        try {
            List<Trainer> trainers = trainerDAO.getAllTrainers();
            if (trainers.isEmpty()) {
                System.out.println("No trainers found.");
            } else {
                for (Trainer t : trainers) {
                    System.out.println(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving trainers: " + e.getMessage());
        }
    }

    public Trainer getTrainerById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'trainer'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Trainer(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            }

        } catch (SQLException e) {
            Logger.getLogger(TrainerService.class.getName()).severe("Error fetching trainer: " + e.getMessage());
        }
        return null;
    }

    public void updateTrainer(Trainer trainer) {
        try {
            trainerDAO.updateTrainer(trainer);
            System.out.println("Trainer updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating trainer: " + e.getMessage());
        }
    }

    public void deleteTrainer(int trainerId) {
        try {
            trainerDAO.deleteTrainer(trainerId);
            System.out.println("Trainer deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting trainer: " + e.getMessage());
        }
    }

    public void viewAssignedClasses(int trainerId) {
        try {
            List<WorkoutClass> classes = trainerDAO.getAssignedClasses(trainerId);
            if (classes.isEmpty()) {
                System.out.println("No assigned classes found.");
            } else {
                for (WorkoutClass wc : classes) {
                    System.out.println(wc);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving trainer classes: " + e.getMessage());
        }
    }

    public void viewRoster(int classId) {
        List<Member> roster = workoutClassService.getClassRoster(classId);
        if (roster.isEmpty()) {
            System.out.println("No members enrolled in this class.");
        } else {
            System.out.println("Members in class " + classId + ":");
            for (Member member : roster) {
                System.out.println(member);
            }
        }
    }

    public void assignToClass(int classId, int trainerId) {
        try {
            trainerDAO.assignTrainerToClass(classId, trainerId);
            System.out.println("Trainer assigned to class.");
        } catch (SQLException e) {
            System.err.println("Error assigning trainer: " + e.getMessage());
        }
    }
}
