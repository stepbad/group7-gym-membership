package com.group7.gym;

import com.group7.gym.models.Trainer;
import com.group7.gym.service.TrainerService;
import com.group7.gym.service.WorkoutClassService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class TrainerMenuTest {

    @Test
    public void testViewProfileAndLogout() {
        // Simulate input: 1 (View Profile), then 10 (Logout)
        String input = "1\n10\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Trainer trainer = new Trainer(1, "john_doe", "hashedpass", "john@example.com", "1234567890", "123 Main St");

        TrainerService trainerService = new TrainerService() {
            @Override
            public Trainer getTrainerById(int id) {
                return trainer; // simulate trainer retrieval
            }
        };

        WorkoutClassService workoutService = new WorkoutClassService(); // could override if needed

        TrainerMenu menu = new TrainerMenu(scanner, trainerService, workoutService, trainer);
        menu.show(); // runs the menu with the simulated input
    }
}
