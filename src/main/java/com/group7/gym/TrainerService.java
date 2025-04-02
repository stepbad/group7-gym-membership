package com.group7.gym;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TrainerService {
    private TrainerDAO trainerDAO;

    public TrainerService(Connection conn) {
        this.trainerDAO = new TrainerDAO(conn);
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

    public void assignToClass(int classId, int trainerId) {
        try {
            trainerDAO.assignTrainerToClass(classId, trainerId);
            System.out.println("Trainer assigned to class.");
        } catch (SQLException e) {
            System.err.println("Error assigning trainer: " + e.getMessage());
        }
    }
}
