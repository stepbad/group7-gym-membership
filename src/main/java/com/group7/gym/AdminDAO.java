package com.group7.gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AdminDAO {
        
    private static final Logger logger = Logger.getLogger(AdminDAO.class.getName());
    private final String url = "jdbc:postgresql://localhost:5432/gym_management";
    private final String user = "postgres";
    private final String password = "your_password";

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

    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO users (username, password_hash, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, 'Admin')";

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
        } catch(SQLException e) {
            logger.severe("Error adding admin: " + e.getMessage());
        }
        return false;
    }

    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE users SET username =?, email =?, phone =?, address =? WHERE user_id =? AND role = 'Admin'";

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

    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM users WHERE user_id =? AND role = 'Admin'";

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
