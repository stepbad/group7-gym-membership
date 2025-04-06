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
     * Adds a new user to the database.
     *
     * @param user User to add
     * @return newly generated user ID if successful, -1 otherwise
     * @throws SQLException if a database error occurs
     */
    public int addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
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
                users.add(buildUserFromResultSet(rs));
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
                return buildUserFromResultSet(rs);
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
                return buildUserFromResultSet(rs);
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
     * Builds a User object from a ResultSet using role-based instantiation.
     *
     * @param rs ResultSet with user data
     * @return Concrete User object (Admin, Trainer, Member)
     * @throws SQLException if a database access error occurs
     */
    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("password_hash");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String address = rs.getString("address");

        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(userId, username, passwordHash, email, phone, address);
            case "trainer":
                return new Trainer(userId, username, passwordHash, email, phone, address);
            case "member":
                return new Member(userId, username, passwordHash, email, phone, address, 0, 0.0);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
