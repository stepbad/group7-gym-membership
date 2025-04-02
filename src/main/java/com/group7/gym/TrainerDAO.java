package com.group7.gym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    private Connection conn;

    public TrainerDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Get all workout classes assigned to this trainer
    public List<WorkoutClass> getAssignedClasses(int trainerId) {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                WorkoutClass wc = new WorkoutClass(
                        rs.getInt("workout_class_id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("trainer_id")
                );
                classes.add(wc);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving classes for trainer: " + e.getMessage());
        }

        return classes;
    }

    public void assignTrainerToClass(int classId, int trainerId) {
        String sql = "UPDATE workout_classes SET trainer_id = ? WHERE workout_class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error assigning trainer to class: " + e.getMessage());
        }
    }
}
