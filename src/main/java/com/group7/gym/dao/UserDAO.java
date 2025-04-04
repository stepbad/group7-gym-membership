package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO class for managing user-related database operations.
 */
public class UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    /**
     * Adds a new user to the database.
     *
     * @param user User to add
     * @return true if successful
     * @throws SQLException if a database error occurs
     */
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing user's information.
     *
     * @param user Updated user object
     * @return true if successful
     * @throws SQLException if a database error occurs
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password_hash = ?, email = ?, phone = ?, address = ?, role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, user.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return List of User objects
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("role")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving all users: " + e.getMessage());
        }

        return users;
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email Email of the user to retrieve
     * @return User object or null if not found
     * @throws SQLException if a database error occurs
     */
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("role")
                );
            }
        }
        return null;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId ID of the user
     * @return User object or null if not found
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving user by ID: " + e.getMessage());
        }

        return user;
    }

    /**
     * Deletes a user from the database by ID.
     *
     * @param userId ID of the user to delete
     * @return true if deletion was successful
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("User deleted successfully.");
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error deleting user: " + e.getMessage());
        }

        return false;
    }
}