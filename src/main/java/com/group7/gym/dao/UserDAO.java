package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.*;

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
                users.add(createUserFromResultSet(rs));
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
                return createUserFromResultSet(rs);
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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving user by ID: " + e.getMessage());
        }

        return null;
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

    /**
     * Creates a specific User subtype (Admin, Trainer, Member) based on role field.
     *
     * @param rs ResultSet with user data
     * @return A User object (Admin, Trainer, or Member)
     * @throws SQLException if field access fails
     */
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        String role = rs.getString("role").toLowerCase();
        switch (role) {
            case "admin":
                return new Admin(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            case "trainer":
                return new Trainer(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            case "member":
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
            default:
                return null;
        }
    }
}
