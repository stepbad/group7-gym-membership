package com.group7.gym.service;

import com.group7.gym.dao.AdminDAO;
import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.Admin;
import com.group7.gym.models.User;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for handling business logic related to Admins and Users.
 */
public class AdminService {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());

    private final AdminDAO adminDAO;
    private final UserDAO userDAO;

    /**
     * Constructs the AdminService and initializes its DAOs.
     */
    public AdminService() {
        this.adminDAO = new AdminDAO();
        this.userDAO = new UserDAO();
    }

    // ----------------- Admin Methods -----------------

    /**
     * Retrieves an admin by ID.
     *
     * @param adminId ID of the admin
     * @return Admin object or null if not found
     */
    public Admin getAdminById(int adminId) {
        Admin admin = adminDAO.getAdminById(adminId);
        if (admin == null) {
            logger.warning("Error: Admin not found with ID " + adminId);
        }
        return admin;
    }

    /**
     * Retrieves all admin users.
     *
     * @return List of Admin objects
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = adminDAO.getAllAdmins();
        if (admins.isEmpty()) {
            logger.info("No admins found.");
        }
        return admins;
    }

    /**
     * Adds a new admin after validating fields.
     *
     * @param admin Admin to add
     * @return true if added successfully
     */
    public boolean addAdmin(Admin admin) {
        if (admin.getUserId() <= 0) {
            logger.warning("Error: Invalid user ID.");
            return false;
        }
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            logger.warning("Error: Invalid username.");
            return false;
        }

        boolean result = adminDAO.addAdmin(admin);
        if (result) {
            logger.info("Admin added successfully.");
        }
        return result;
    }

    /**
     * Updates an existing admin after validation.
     *
     * @param admin Updated admin object
     * @return true if update successful
     */
    public boolean updateAdmin(Admin admin) {
        if (admin.getUserId() <= 0) {
            logger.warning("Error: Invalid user ID.");
            return false;
        }
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            logger.warning("Error: Invalid username.");
            return false;
        }
        if (adminDAO.getAdminById(admin.getUserId()) == null) {
            logger.warning("Error: Admin not found with ID " + admin.getUserId());
            return false;
        }

        boolean result = adminDAO.updateAdmin(admin);
        if (result) {
            logger.info("Admin updated successfully.");
        }
        return result;
    }

    /**
     * Deletes an admin by ID after verifying existence.
     *
     * @param adminId ID of the admin to delete
     * @return true if deleted
     */
    public boolean deleteAdmin(int adminId) {
        if (adminDAO.getAdminById(adminId) == null) {
            logger.warning("Error: Admin not found. Cannot delete.");
            return false;
        }
        boolean result = adminDAO.deleteAdmin(adminId);
        if (result) {
            logger.info("Admin deleted successfully.");
        }
        return result;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return List of User objects
     */
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            logger.info("No users found.");
        }
        return users;
    }

    /**
     * Deletes a user by ID after verifying existence.
     *
     * @param userId ID of the user to delete
     * @return true if deleted successfully
     */
    public boolean deleteUser(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            logger.warning("Error: User not found. Cannot delete.");
            return false;
        }
        boolean result = userDAO.deleteUser(userId);
        if (result) {
            logger.info("User deleted successfully.");
        }
        return result;
    }
}
