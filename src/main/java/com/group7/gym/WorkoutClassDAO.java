package com.group7.gym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDAO {
    private Connection conn;

    public WorkoutClassDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "INSERT INTO workout_classes (type, description, trainer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, wc.getType());
            stmt.setString(2, wc.getDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.executeUpdate();
        }
    }

    // READ: Get all classes
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

    // READ: Get one by ID
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

    // UPDATE
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

    // DELETE
    public void deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

