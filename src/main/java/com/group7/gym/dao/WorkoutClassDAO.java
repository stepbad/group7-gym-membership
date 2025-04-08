package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.WorkoutClass;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO class for managing workout class records in the database.
 */
public class WorkoutClassDAO {

    private static final Logger logger = Logger.getLogger(WorkoutClassDAO.class.getName());

    /**
     * Adds a new workout class to the database.
     *
     * @param wc WorkoutClass object to add
     * @throws SQLException if a database error occurs
     */
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "INSERT INTO workout_classes (type, description, trainer_id, start_time, end_time, class_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wc.getType());
            stmt.setString(2, wc.getDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.setTime(4, Time.valueOf(wc.getStartTime()));
            stmt.setTime(5, Time.valueOf(wc.getEndTime()));
            stmt.setDate(6, Date.valueOf(wc.getClassDate()));

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all workout classes from the database.
     *
     * @return List of WorkoutClass objects
     * @throws SQLException if a database error occurs
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> list = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                WorkoutClass wc = new WorkoutClass(
                    rs.getInt("workout_class_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getInt("trainer_id"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime(),
                    rs.getDate("class_date").toLocalDate()
                );
                list.add(wc);
            }
        }

        return list;
    }

    /**
     * Retrieves a workout class by ID.
     *
     * @param id Workout class ID
     * @return WorkoutClass object or null if not found
     * @throws SQLException if a database error occurs
     */
    public WorkoutClass getWorkoutClassById(int id) throws SQLException {
        String sql = "SELECT workout_class_id, type, description, trainer_id, start_time, end_time, class_date " +
                "FROM workout_classes WHERE workout_class_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new WorkoutClass(
                    rs.getInt("workout_class_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getInt("trainer_id"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime(),
                    rs.getDate("class_date").toLocalDate()
                );
            }
        }

        return null;
    }

    /**
     * Updates an existing workout class in the database.
     *
     * @param wc WorkoutClass object with updated values
     * @throws SQLException if a database error occurs
     */
    public void updateWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "UPDATE workout_classes SET type = ?, description = ?, trainer_id = ?, " +
                     "start_time = ?, end_time = ?, class_date = ? WHERE workout_class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wc.getType());
            stmt.setString(2, wc.getDescription());
            stmt.setInt(3, wc.getTrainerId());
            stmt.setTime(4, Time.valueOf(wc.getStartTime()));
            stmt.setTime(5, Time.valueOf(wc.getEndTime()));
            stmt.setDate(6, Date.valueOf(wc.getClassDate()));
            stmt.setInt(7, wc.getWorkoutClassId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a workout class by ID.
     *
     * @param id ID of the class to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteWorkoutClass(int id) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE workout_class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
