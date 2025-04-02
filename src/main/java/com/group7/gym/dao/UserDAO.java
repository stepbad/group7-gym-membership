package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.group7.gym.models.Admin;
import com.group7.gym.models.Member;
import com.group7.gym.models.Trainer;
import com.group7.gym.models.User;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void createNewUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.executeUpdate();
        }
    }

    // READ: Get one user by email (for login)
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return buildUserFromResultSet(rs);
            }
        }
        return null;
    }

    // READ: Get all users
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(buildUserFromResultSet(rs));
            }
        }
        return users;
    }

    // UPDATE
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, address = ?, role = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, user.getUserId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // Helper: Builds User, Admin, Trainer, Member object based on role
    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String address = rs.getString("address");
        String role = rs.getString("role");

        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(id, password, email, phone, address); // uses existing constructor
            case "trainer":
                return new Trainer(id, username, password, email, phone, address);
            case "member":
                return new Member(id, username, password, email, phone, address, 0, 0.0); // dummy data
            default:
                return null;
        }
    }
}
