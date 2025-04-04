package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.group7.gym.models.Admin;

/**
 * DAO class for managing Admin-related database operations.
 */
public class AdminDAO {

    private static final Logger logger = Logger.getLogger(AdminDAO.class.getName());
    private final String url = "jdbc:postgresql://localhost:5432/gym_management";
    private final String user = "postgres";
    private final String password = "your_password";

    /**
     * Retrieves an admin by user ID.
     *
     * @param adminId Admin's user ID
     * @return Admin object or null if not found
     */
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'Admin'";
        Admin admin = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving admin: " + e.getMessage());
        }
        return admin;
    }

    /**
     * Retrieves all admin users from the database.
     *
     * @return List of Admins
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'Admin'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving admins: " + e.getMessage());
        }
        return admins;
    }

    /**
     * Inserts a new admin into the database.
     *
     * @param admin Admin to add
     * @return true if successful
     */
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO users (user_id, username, password_hash, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, 'Admin')";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, admin.getUserId());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getPasswordHash());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getPhone());
            stmt.setString(6, admin.getAddress());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Admin added successfully.");
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error adding admin: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates an existing admin in the database.
     *
     * @param admin Updated admin object
     * @return true if successful
     */
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ? WHERE user_id = ? AND role = 'Admin'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPhone());
            stmt.setString(4, admin.getAddress());
            stmt.setInt(5, admin.getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Admin updated successfully.");
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error updating admin: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes an admin by ID.
     *
     * @param adminId Admin user ID to delete
     * @return true if deleted
     */
    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'Admin'";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Admin deleted successfully.");
                return true;
            }
        } catch (SQLException e) {
            logger.severe("Error deleting admin: " + e.getMessage());
        }
        return false;
    }
}
