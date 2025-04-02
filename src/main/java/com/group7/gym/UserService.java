package com.group7.gym;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private UserDAO userDAO;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(Connection conn) {
        this.userDAO = new UserDAO(conn);
    }

    public void registerUser(User user) {
        try {
            if (user.getEmail() == null || user.getPasswordHash() == null) {
                throw new IllegalArgumentException("Email and password required.");
            }

            if (userDAO.getUserByEmail(user.getEmail()) != null) {
                logger.log(Level.WARNING, "User already exists: " + user.getEmail());
                System.out.println("Email is already registered.");
                return;
            }

            userDAO.createNewUser(user);
            System.out.println("User registered successfully.");

        } catch (SQLException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Registration failed: " + e.getMessage());
        }
    }

    public User loginUser(String email, String passwordPlaintext) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && PasswordUtils.checkPassword(passwordPlaintext, user.getPasswordHash())) {
                logger.log(Level.INFO, "Login successful: " + email);
                return user;
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Login failed: " + e.getMessage());
        }
        return null;
    }

    public void listAllUsers() {
        try {
            List<User> users = userDAO.getAllUsers();
            for (User u : users) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving users: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
            System.out.println("User updated.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating user: " + e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        try {
            userDAO.deleteUserById(userId);
            System.out.println("User deleted.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting user: " + e.getMessage());
        }
    }
}
