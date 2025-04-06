package com.group7.gym.service;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.*;
import com.group7.gym.utils.PasswordUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing business logic related to users.
 */
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserDAO userDAO;

    /**
     * Constructs the UserService and initializes the DAO.
     */
    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Registers a new user after validation.
     *
     * @param user User to register
     */
    public void registerUser(User user) {
        try {
            if (user.getEmail() == null || user.getPasswordHash() == null || user.getRole() == null) {
                throw new IllegalArgumentException("Email, password, and role are required.");
            }

            if (!isValidRole(user.getRole())) {
                System.out.println("Invalid role: must be admin, trainer, or member.");
                return;
            }

            if (userDAO.getUserByEmail(user.getEmail()) != null) {
                System.out.println("Email is already registered.");
                return;
            }

            // Hash the raw password before saving
            user.setPasswordHash(PasswordUtils.hashPassword(user.getPasswordHash()));

            userDAO.addUser(user);
            System.out.println("User registered successfully.");
        } catch (SQLException | IllegalArgumentException e) {
            logger.severe("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Logs in a user by checking password hash.
     *
     * @param email Email of the user
     * @param passwordPlaintext Plaintext password
     * @return Authenticated User object or null
     */
    public User loginUser(String email, String passwordPlaintext) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                System.out.println("DEBUG - Stored hash: " + user.getPasswordHash());
                System.out.println("DEBUG - Entered password: " + passwordPlaintext);
                if (PasswordUtils.checkPassword(passwordPlaintext, user.getPasswordHash())) {
                    logger.info("Login successful: " + email);
                    return user;
                }
            }
            System.out.println("Invalid credentials.");
        } catch (SQLException e) {
            logger.severe("Login failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lists all users in the system.
     */
    public void listAllUsers() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    /**
     * Updates user details.
     *
     * @param user Updated user object
     */
    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
            System.out.println("User updated.");
        } catch (SQLException e) {
            logger.severe("Error updating user: " + e.getMessage());
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId ID of the user to delete
     */
    public void deleteUser(int userId) {
        boolean success = userDAO.deleteUser(userId);
        if (success) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found or could not be deleted.");
        }
    }

    /**
     * Helper method to check if a role is valid.
     */
    private boolean isValidRole(String role) {
        return role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("trainer") || role.equalsIgnoreCase("member");
    }
}
