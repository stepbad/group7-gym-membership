package com.group7.gym.dao;

import com.group7.gym.models.Trainer;
import com.group7.gym.models.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void testAddAndGetTrainerByEmail() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "testTrainer", "12345678", email, "555-5555", "123 Gym Street");
        int userId = userDAO.addUser(trainer);
        assertTrue(userId > 0, "Trainer should be added and return a valid ID");

        User fetched = userDAO.getUserByEmail(email);
        assertNotNull(fetched, "Trainer should be retrieved");
        assertEquals(email, fetched.getEmail());
        assertEquals("trainer", fetched.getRole());
    }

    @Test
    void testUpdateTrainer() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "trainerUpdate", "12345678", email, "555-0000", "456 Fit Ave");
        int userId = userDAO.addUser(trainer);
        trainer.setUserId(userId);

        Trainer updatedTrainer = new Trainer(userId, "updatedName", "87654321", email, "999-9999", "789 Muscle Rd");
        boolean updated = userDAO.updateUser(updatedTrainer);
        assertTrue(updated, "Trainer should be updated");

        User fetched = userDAO.getUserByEmail(email);
        assertEquals("updatedName", fetched.getUsername());
        assertEquals("999-9999", fetched.getPhone());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 0, "Should return a list of users");
    }

    @Test
    void testDeleteTrainer() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "trainerDelete", "12345678", email, "000-0000", "Delete St");
        int userId = userDAO.addUser(trainer);
        trainer.setUserId(userId);

        boolean deleted = userDAO.deleteUser(userId);
        assertTrue(deleted, "Trainer should be deleted");

        User fetched = userDAO.getUserById(userId);
        assertNull(fetched, "Deleted trainer should not be found");
    }

    @AfterEach
    void tearDown() {
        // Optional cleanup logic
    }
}
