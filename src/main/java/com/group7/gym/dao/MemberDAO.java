package com.group7.gym.dao;

import com.group7.gym.models.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing Member database operations.
 */
public class MemberDAO {
    private Connection conn;

    /**
     * Constructs a MemberDAO with the provided database connection.
     *
     * @param conn Active database connection
     */
    public MemberDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a new member to the database.
     *
     * @param member Member to add
     * @throws SQLException if a database error occurs
     */
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, phone, address, role, membership_id, total_membership_expenses) " +
                     "VALUES (?, ?, ?, ?, ?, 'member', ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getUsername());
            stmt.setString(2, member.getPasswordHash());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getAddress());
            stmt.setInt(6, member.getMembershipId());
            stmt.setDouble(7, member.getTotalMembershipExpenses());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all members from the database.
     *
     * @return List of Member objects
     * @throws SQLException if a database error occurs
     */
    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'member'";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getInt("membership_id"),
                    rs.getDouble("total_membership_expenses")
                );
                members.add(member);
            }
        }
        return members;
    }

    /**
     * Retrieves a member by their ID.
     *
     * @param memberId Member user ID
     * @return Member object or null if not found
     * @throws SQLException if a database error occurs
     */
    public Member getMemberById(int memberId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'member'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getInt("membership_id"),
                    rs.getDouble("total_membership_expenses")
                );
            }
        }
        return null;
    }

    /**
     * Updates an existing member in the database.
     *
     * @param member Member with updated values
     * @throws SQLException if a database error occurs
     */
    public void updateMember(Member member) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, address = ?, " +
                     "membership_id = ?, total_membership_expenses = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getUsername());
            stmt.setString(2, member.getPasswordHash());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getAddress());
            stmt.setInt(6, member.getMembershipId());
            stmt.setDouble(7, member.getTotalMembershipExpenses());
            stmt.setInt(8, member.getUserId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a member from the database.
     *
     * @param memberId ID of the member to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'member'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            stmt.executeUpdate();
        }
    }
}
