package com.group7.gym.dao;

import com.group7.gym.models.Trainer;
import com.group7.gym.models.WorkoutClass;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutClassDAOTest {

    private WorkoutClassDAO workoutClassDAO;
    private TrainerDAO trainerDAO;
    private int trainerId;

    @BeforeEach
    void setUp() throws SQLException {
        workoutClassDAO = new WorkoutClassDAO();
        trainerDAO = new TrainerDAO();

        String email = "trainer_wc_" + UUID.randomUUID() + "@example.com";
        Trainer trainer = new Trainer(0, "TrainerWC", "12345678", email, "555-1111", "Test Blvd");
        trainerId = new UserDAO().addUser(trainer);
        trainer.setUserId(trainerId);
    }

    @Test
    void testAddAndGetWorkoutClassById() throws SQLException {
        WorkoutClass wc = new WorkoutClass(0, "Yoga", "Morning stretch", trainerId,
                LocalTime.of(9, 0), LocalTime.of(10, 0), LocalDate.now());

        workoutClassDAO.addWorkoutClass(wc);

        List<WorkoutClass> all = workoutClassDAO.getAllWorkoutClasses();
        WorkoutClass added = all.stream()
                .filter(c -> c.getType().equals("Yoga") && c.getTrainerId() == trainerId)
                .findFirst().orElse(null);

        assertNotNull(added);
        WorkoutClass fetched = workoutClassDAO.getWorkoutClassById(added.getWorkoutClassId());
        assertNotNull(fetched);
        assertEquals("Morning stretch", fetched.getDescription());
    }

    @Test
    void testUpdateWorkoutClass() throws SQLException {
        WorkoutClass wc = new WorkoutClass(0, "Pilates", "Core strength", trainerId,
                LocalTime.of(11, 0), LocalTime.of(12, 0), LocalDate.now());

        workoutClassDAO.addWorkoutClass(wc);

        WorkoutClass added = workoutClassDAO.getAllWorkoutClasses().stream()
                .filter(c -> c.getType().equals("Pilates"))
                .findFirst().orElseThrow();

        added.setDescription("Updated core strength");
        workoutClassDAO.updateWorkoutClass(added);

        WorkoutClass updated = workoutClassDAO.getWorkoutClassById(added.getWorkoutClassId());
        assertEquals("Updated core strength", updated.getDescription());
    }

    @Test
    void testDeleteWorkoutClass() throws SQLException {
        WorkoutClass wc = new WorkoutClass(0, "Boxing", "Heavy bag", trainerId,
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.now());

        workoutClassDAO.addWorkoutClass(wc);

        WorkoutClass added = workoutClassDAO.getAllWorkoutClasses().stream()
                .filter(c -> c.getType().equals("Boxing"))
                .findFirst().orElseThrow();

        workoutClassDAO.deleteWorkoutClass(added.getWorkoutClassId());
        WorkoutClass result = workoutClassDAO.getWorkoutClassById(added.getWorkoutClassId());
        assertNull(result);
    }

    @AfterEach
    void tearDown() {
        // Optional: clean up created trainer
    }
}
