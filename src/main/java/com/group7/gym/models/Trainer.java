package com.group7.gym.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Trainer user in the gym system.
 * A Trainer can be assigned to multiple workout classes.
 */
public class Trainer extends User {

    /** List of workout class IDs assigned to this trainer. */
    private List<Integer> assignedClassIds;

    /**
     * Constructs a new Trainer with the given details.
     *
     * @param userId        Unique user ID
     * @param username      Username
     * @param passwordHash  Hashed password
     * @param email         Email address
     * @param phone         Phone number
     * @param address       Physical address
     */
    public Trainer(int userId, String username, String passwordHash, String email, String phone, String address) {
        super(userId, username, passwordHash, email, phone, address, "trainer");
        this.assignedClassIds = new ArrayList<>();
    }

    /**
     * Assigns a workout class to the trainer by ID.
     *
     * @param classId ID of the class to assign
     */
    public void assignClass(int classId) {
        if (!assignedClassIds.contains(classId)) {
            assignedClassIds.add(classId);
        }
    }

    /**
     * Removes a workout class assignment by ID.
     *
     * @param classId ID of the class to remove
     */
    public void removeClass(int classId) {
        assignedClassIds.remove(Integer.valueOf(classId));
    }

    /**
     * Gets a list of assigned class IDs.
     *
     * @return List of workout class IDs
     */
    public List<Integer> getAssignedClassIds() {
        return assignedClassIds;
    }

    /**
     * Sets the assigned class IDs for this trainer.
     *
     * @param assignedClassIds List of class IDs
     */
    public void setAssignedClassIds(List<Integer> assignedClassIds) {
        this.assignedClassIds = assignedClassIds;
    }

    /**
     * Returns a string representation of the Trainer object.
     *
     * @return String summary of trainer
     */
    @Override
    public String toString() {
        return "Trainer{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", assignedClassIds=" + assignedClassIds +
                '}';
    }
}
