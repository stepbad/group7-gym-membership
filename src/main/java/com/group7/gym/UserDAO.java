package com.group7.gym;

import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO() {
        this.conn = DBConnection.getConnection(); // Make sure this class exists
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String passwordHash = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String role = rs.getString("role");

                switch (role.toLowerCase()) {
                    case "admin":
                        return new Admin(id, passwordHash, email, phone, address);
                    case "trainer":
                        return new Trainer(id, username, passwordHash, email, phone, address);
                    case "member":
                        return new Member(id, username, passwordHash, email, phone, address, 0, 0.0); // dummy values
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    public void createNewUser(User user) {
        String sql = "INSERT INTO users (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    public void deleteUserById(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
}
