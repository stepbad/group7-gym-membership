package com.group7.gym;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private UserDAO userDAO = new UserDAO();  // Adjust if you use constructor injection
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public void registerUser(User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Please provide email and password.");
            }

            if (userDAO.getUserByEmail(user.getEmail()) != null) {
                logger.log(Level.WARNING, "Email already registered: " + user.getEmail());
                throw new IllegalArgumentException("Email already exists");
            }

            String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            userDAO.createNewUser(user);
            logger.log(Level.INFO, "User registered: " + user.getEmail());

        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public User loginUser(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && PasswordUtils.checkPassword(password, user.getPassword())) {
            logger.log(Level.INFO, "Login successful for: " + email);
            return user;
        } else {
            logger.log(Level.WARNING, "Invalid credentials for: " + email);
            return null;
        }
    }
}
