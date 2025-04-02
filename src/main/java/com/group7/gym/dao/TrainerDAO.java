package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.group7.gym.models.Trainer;
import com.group7.gym.models.WorkoutClass;

public class TrainerDAO {
    private Connection conn;

    public TrainerDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE Trainer
    public void addTrainer(Trainer trainer) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, 'Trainer')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trainer.getUsername());
            stmt.setString(2, trainer.getPasswordHash());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getPhone());
            stmt.setString(5, trainer.getAddress());
            stmt.executeUpdate();
        }
    }

    // READ All Trainers
    public List<Trainer> getAllTrainers() throws SQLException {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'Trainer'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Trainer trainer = new Trainer(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
                trainers.add(trainer);
            }
        }
        return trainers;
    }

    // READ Single Trainer by ID
    public Trainer getTrainerById(int trainerId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'Trainer'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Trainer(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            }
        }
        return null;
    }

    // UPDATE Trainer Info
    public void updateTrainer(Trainer trainer) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trainer.getUsername());
            stmt.setString(2, trainer.getPasswordHash());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getPhone());
            stmt.setString(5, trainer.getAddress());
            stmt.setInt(6, trainer.getUserId());
            stmt.executeUpdate();
        }
    }

    // DELETE Trainer
    public void deleteTrainer(int trainerId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'Trainer'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            stmt.executeUpdate();
        }
    }

    // Get All Classes by Trainer
    public List<WorkoutClass> getAssignedClasses(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classes.add(new WorkoutClass(
                    rs.getInt("workout_class_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getInt("trainer_id")
                ));
            }
        }
        return classes;
    }

    public void assignTrainerToClass(int classId, int trainerId) throws SQLException {
        String sql = "UPDATE workout_classes SET trainer_id = ? WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        }
    }
}
