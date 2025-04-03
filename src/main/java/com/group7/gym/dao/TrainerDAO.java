package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.group7.gym.models.Trainer;
import com.group7.gym.models.WorkoutClass;

/**
 * DAO class for managing Trainer-related database operations.
 */
public class TrainerDAO {
    private Connection conn;

    /**
     * Constructs a TrainerDAO with the given database connection.
     *
     * @param conn Active database connection
     */
    public TrainerDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a new trainer to the database.
     *
     * @param trainer Trainer object to add
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Retrieves all trainers from the database.
     *
     * @return List of Trainer objects
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Retrieves a trainer by ID.
     *
     * @param trainerId ID of the trainer
     * @return Trainer object or null if not found
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Updates trainer information in the database.
     *
     * @param trainer Updated trainer object
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Deletes a trainer from the database.
     *
     * @param trainerId ID of the trainer to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteTrainer(int trainerId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'Trainer'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all workout classes assigned to a trainer.
     *
     * @param trainerId ID of the trainer
     * @return List of WorkoutClass objects
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Assigns a trainer to a workout class by updating the trainer_id.
     *
     * @param classId   ID of the class
     * @param trainerId ID of the trainer
     * @throws SQLException if a database error occurs
     */
    public void assignTrainerToClass(int classId, int trainerId) throws SQLException {
        String sql = "UPDATE workout_classes SET trainer_id = ? WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        }
    }
}
