package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.group7.gym.models.WorkoutClass;

/**
 * Data Access Object for handling CRUD operations for WorkoutClass.
 */
public class WorkoutClassDAO {
    private Connection conn;

    /**
     * Constructs the DAO with an existing DB connection.
     *
     * @param conn Active DB connection
     */
    public WorkoutClassDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a new workout class to the database.
     *
     * @param wc WorkoutClass object to add
     * @throws SQLException if DB operation fails
     */
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "INSERT INTO workout_classes (type, description, trainer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, wc.getType());
            stmt.setString(2, wc.getDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all workout classes from the database.
     *
     * @return List of WorkoutClass objects
     * @throws SQLException if DB operation fails
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> list = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new WorkoutClass(
                        rs.getInt("workout_class_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("trainer_id")
                ));
            }
        }
        return list;
    }

    /**
     * Retrieves a single workout class by ID.
     *
     * @param id Workout class ID
     * @return WorkoutClass or null if not found
     * @throws SQLException if DB operation fails
     */
    public WorkoutClass getWorkoutClassById(int id) throws SQLException {
        String sql = "SELECT * FROM workout_classes WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new WorkoutClass(
                        rs.getInt("workout_class_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("trainer_id")
                );
            }
        }
        return null;
    }

    /**
     * Updates an existing workout class in the database.
     *
     * @param wc Updated WorkoutClass object
     * @throws SQLException if DB operation fails
     */
    public void updateWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "UPDATE workout_classes SET type = ?, description = ?, trainer_id = ? WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, wc.getType());
            stmt.setString(2, wc.getDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.setInt(4, wc.getWorkoutClassId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a workout class from the database.
     *
     * @param id ID of the class to delete
     * @throws SQLException if DB operation fails
     */
    public void deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
