package com.group7.gym.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.group7.gym.dao.TrainerDAO;
import com.group7.gym.models.Trainer;
import com.group7.gym.models.WorkoutClass;

/**
 * Service class for trainer-related business logic.
 */
public class TrainerService {
    private TrainerDAO trainerDAO;

    /**
     * Constructs the service with a database connection.
     *
     * @param conn Active database connection
     */
    public TrainerService(Connection conn) {
        this.trainerDAO = new TrainerDAO(conn);
    }

    /**
     * Registers a new trainer.
     *
     * @param trainer Trainer to register
     */
    public void registerTrainer(Trainer trainer) {
        try {
            trainerDAO.addTrainer(trainer);
            System.out.println("Trainer registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering trainer: " + e.getMessage());
        }
    }

    /**
     * Lists all registered trainers.
     */
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

    /**
     * Displays a trainer's details by ID.
     *
     * @param trainerId Trainer ID
     */
    public void viewTrainer(int trainerId) {
        try {
            Trainer t = trainerDAO.getTrainerById(trainerId);
            if (t != null) {
                System.out.println(t);
            } else {
                System.out.println("Trainer not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving trainer: " + e.getMessage());
        }
    }

    /**
     * Updates a trainer’s information.
     *
     * @param trainer Updated trainer object
     */
    public void updateTrainer(Trainer trainer) {
        try {
            trainerDAO.updateTrainer(trainer);
            System.out.println("Trainer updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating trainer: " + e.getMessage());
        }
    }

    /**
     * Deletes a trainer by ID.
     *
     * @param trainerId ID of the trainer to delete
     */
    public void deleteTrainer(int trainerId) {
        try {
            trainerDAO.deleteTrainer(trainerId);
            System.out.println("Trainer deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting trainer: " + e.getMessage());
        }
    }

    /**
     * Displays all workout classes assigned to a trainer.
     *
     * @param trainerId Trainer ID
     */
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

    /**
     * Assigns a trainer to a workout class.
     *
     * @param classId   Workout class ID
     * @param trainerId Trainer ID
     */
    public void assignToClass(int classId, int trainerId) {
        try {
            trainerDAO.assignTrainerToClass(classId, trainerId);
            System.out.println("Trainer assigned to class.");
        } catch (SQLException e) {
            System.err.println("Error assigning trainer: " + e.getMessage());
        }
    }
}
