package com.group7.gym.dao;

import com.group7.gym.models.Trainer;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainerDAOTest {

    private TrainerDAO trainerDAO;

    @BeforeEach
    void setUp() {
        trainerDAO = new TrainerDAO();
    }

    @Test
    void testAddAndGetTrainerById() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "trainer_dao_test", "12345678", email, "555-1234", "Test Lane");
        int userId = new UserDAO().addUser(trainer);
        trainer.setUserId(userId);

        Trainer fetched = trainerDAO.getTrainerById(userId);
        assertNotNull(fetched, "Trainer should be added and retrievable");
        assertEquals("trainer_dao_test", fetched.getUsername());
    }

    @Test
    void testUpdateTrainer() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "update_trainer", "pass", email, "000-0000", "Nowhere");
        int userId = new UserDAO().addUser(trainer);
        trainer.setUserId(userId);

        trainer.setUsername("updated_name");
        trainer.setPhone("111-2222");
        trainerDAO.updateTrainer(trainer);

        Trainer updated = trainerDAO.getTrainerById(userId);
        assertEquals("updated_name", updated.getUsername());
        assertEquals("111-2222", updated.getPhone());
    }

    @Test
    void testDeleteTrainer() throws SQLException {
        String email = "trainer_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "delete_trainer", "pass", email, "123-0000", "Void St");
        int userId = new UserDAO().addUser(trainer);
        trainer.setUserId(userId);

        trainerDAO.deleteTrainer(userId);
        Trainer result = trainerDAO.getTrainerById(userId);
        assertNull(result, "Trainer should be deleted");
    }

    @AfterEach
    void tearDown() {
        // Optional cleanup
    }
}
