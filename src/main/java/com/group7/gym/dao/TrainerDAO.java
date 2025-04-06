package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.Trainer;
import com.group7.gym.models.WorkoutClass;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO class for managing Trainer-related database operations.
 */
public class TrainerDAO {

    private static final Logger logger = Logger.getLogger(TrainerDAO.class.getName());

    /**
     * Adds a new trainer to the database.
     *
     * @param trainer Trainer object to add
     * @throws SQLException if a database error occurs
     */
    public void addTrainer(Trainer trainer) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, email, phone, address, role) VALUES (?, ?, ?, ?, ?, 'trainer')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        String sql = "SELECT * FROM users WHERE role = 'trainer'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trainer trainer = new Trainer(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
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
     * @param trainerId Trainer's user ID
     * @return Trainer object or null if not found
     * @throws SQLException if a database error occurs
     */
    public Trainer getTrainerById(int trainerId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'trainer'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
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
        }
        return null;
    }

    /**
     * Updates a trainer's information.
     *
     * @param trainer Trainer object with updated values
     * @throws SQLException if a database error occurs
     */
    public void updateTrainer(Trainer trainer) throws SQLException {
        String sql = "UPDATE users SET username = ?, password_hash = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
     * @param trainerId Trainer user ID to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteTrainer(int trainerId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'trainer'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                WorkoutClass workoutClass = new WorkoutClass(
                        rs.getInt("workout_class_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("trainer_id"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getDate("class_date").toLocalDate()
                );
                classes.add(workoutClass);
            }
        }

        return classes;
    }

    /**
     * Assigns a trainer to a workout class.
     *
     * @param classId   Workout class ID
     * @param trainerId Trainer user ID
     * @throws SQLException if a database error occurs
     */
    public void assignTrainerToClass(int classId, int trainerId) throws SQLException {
        String sql = "UPDATE workout_classes SET trainer_id = ? WHERE workout_class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        }
    }
}
