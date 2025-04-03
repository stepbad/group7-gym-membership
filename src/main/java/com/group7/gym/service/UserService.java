package com.group7.gym.service;

import com.group7.gym.dao.UserDAO;
import com.group7.gym.models.User;
import com.group7.gym.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for user-related business logic.
 */
public class UserService {
    private final UserDAO userDAO;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(Connection conn) {
        this.userDAO = new UserDAO(conn);
    }

    /**
     * Registers a new user after validation.
     */
    public void registerUser(User user) {
        try {
            if (user.getEmail() == null || user.getPasswordHash() == null) {
                throw new IllegalArgumentException("Email and password required.");
            }

            if (userDAO.getUserByEmail(user.getEmail()) != null) {
                System.out.println("Email already registered.");
                return;
            }

            userDAO.createNewUser(user);
            System.out.println("User registered successfully.");
        } catch (SQLException | IllegalArgumentException e) {
            logger.severe("Registration failed: " + e.getMessage());
        }
    }

    /**
     * Logs in a user by checking password hash.
     */
    public User loginUser(String email, String passwordPlaintext) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && PasswordUtils.checkPassword(passwordPlaintext, user.getPasswordHash())) {
                logger.info("Login successful: " + email);
                return user;
            }
            System.out.println("Invalid credentials.");
        } catch (SQLException e) {
            logger.severe("Login failed: " + e.getMessage());
        }
        return null;
    }

    public void listAllUsers() {
        try {
            List<User> users = userDAO.getAllUsers();
            users.forEach(System.out::println);
        } catch (SQLException e) {
            logger.severe("Error retrieving users: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
            System.out.println("User updated.");
        } catch (SQLException e) {
            logger.severe("Error updating user: " + e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        try {
            userDAO.deleteUserById(userId);
            System.out.println("User deleted.");
        } catch (SQLException e) {
            logger.severe("Error deleting user: " + e.getMessage());
        }
    }
}
