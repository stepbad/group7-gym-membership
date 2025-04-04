package com.group7.gym.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 */
public class PasswordUtils {

    /**
     * Hashes a plaintext password using BCrypt.
     *
     * @param plainPassword The plaintext password to hash
     * @return The hashed password string
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Verifies a plaintext password against a stored hashed password.
     *
     * @param plainPassword  The raw password input to verify
     * @param hashedPassword The stored hash to compare against
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
