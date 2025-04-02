package com.group7.gym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    private Connection conn;

    public TrainerDAO(Connection conn) {
        this.conn = conn;
    }

    public List<WorkoutClass> getAssignedClasses(int trainerId) throws SQLException {
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
