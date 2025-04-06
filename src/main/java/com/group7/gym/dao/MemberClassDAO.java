package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.Member;

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
     * Returns a list of members assigned to a specific class.
     *
     * @param classId ID of the workout class
     * @return List of Member objects
     */
    public List<Member> getMembersByClassId(int classId) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.password_hash, u.email, u.phone, u.address, m.membership_id, m.balance " +
                     "FROM member_class mc " +
                     "JOIN users u ON mc.member_id = u.user_id " +
                     "JOIN members m ON mc.member_id = m.user_id " +
                     "WHERE mc.class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, classId);
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
} 
