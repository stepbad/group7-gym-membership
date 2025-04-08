package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.Member;
import com.group7.gym.models.WorkoutClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO for managing the member_class relationship.
 */
public class MemberClassDAO {
    private static final Logger logger = Logger.getLogger(MemberClassDAO.class.getName());

    /**
     * Enrolls a member into a workout class.
     * @param memberId ID of the member
     * @param classId ID of the class
     * @return true if enrollment successful, false otherwise
     */
    public boolean enrollMember(int memberId, int classId) {
        String sql = "INSERT INTO member_class (member_id, workout_class_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, classId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.warning("Error enrolling member: " + e.getMessage());
            return false;
        }
    }


    /**
     * Returns a list of members assigned to a specific class.
     *
     * @param workoutClassId ID of the workout class
     * @return List of Member objects
     */
    public List<Member> getMembersByClassId(int workoutClassId) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.password_hash, u.email, u.phone, u.address, m.membership_id, m.balance " +
                "FROM member_class mc " +
                "JOIN users u ON mc.member_id = u.user_id " +
                "JOIN memberships m ON mc.member_id = m.member_id " +
                "WHERE mc.workout_class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workoutClassId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("membership_id"),
                        rs.getDouble("balance")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching members for class: " + e.getMessage());
        }

        return members;
    }

    /**
     * Retrieves a list of classes that a member is enrolled in.
     *
     * @param memberId The ID of the member
     * @return List of WorkoutClass objects
     * @throws SQLException if a database error occurs
     */

    public List<WorkoutClass> getClassesByMemberId(int memberId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT wc.workout_class_id, wc.type, wc.description, wc.trainer_id, wc.start_time, wc.end_time, wc.class_date " +
                "FROM workout_classes wc " +
                "JOIN member_class mc ON wc.workout_class_id = mc.workout_class_id " +
                "WHERE mc.member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    WorkoutClass workoutClass = new WorkoutClass(
                            rs.getInt("workout_class_id"),
                            rs.getString("type"),        // Using 'type' instead of 'className'
                            rs.getString("description"),
                            rs.getInt("trainer_id"),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime(),
                            rs.getDate("class_date").toLocalDate()
                    );
                    classes.add(workoutClass);
                }
            }
        }

        return classes;
    }

    /**
     * Checks if a member is enrolled in a specific class.
     *
     * @param memberId The ID of the member
     * @param workoutClassId  The ID of the class
     * @return true if the member is enrolled, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean isMemberEnrolledInClass(int memberId, int workoutClassId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM member_class WHERE member_id = ? AND workout_class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, workoutClassId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // If count > 0, member is enrolled
                }
            }
        }

        return false;
    }

    /**
     * Adds a member to a workout class.
     *
     * @param memberId The ID of the member
     * @param workoutClassId  The ID of the class
     * @throws SQLException if a database error occurs
     */

    public void addMemberToClass(int memberId, int workoutClassId) throws SQLException {
        String sql = "INSERT INTO member_class (member_id, workout_class_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.setInt(2, workoutClassId);
            stmt.executeUpdate();
        }
    }



} 
