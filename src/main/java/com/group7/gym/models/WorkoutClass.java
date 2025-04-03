package com.group7.gym.models;

/**
 * Represents a workout class in the gym system.
 */
public class WorkoutClass {
    private int workoutClassId;
    private String type;
    private String description;
    private int trainerId;

    /**
     * Constructs a WorkoutClass with a known ID (used when loading from DB).
     *
     * @param workoutClassId Class ID
     * @param type           Type of workout (e.g., Yoga, Cardio)
     * @param description    Class description
     * @param trainerId      Assigned trainer's ID
     */
    public WorkoutClass(int workoutClassId, String type, String description, int trainerId) {
        this.workoutClassId = workoutClassId;
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }

    /**
     * Constructs a WorkoutClass without an ID (used before insertion).
     *
     * @param type        Type of workout
     * @param description Class description
     * @param trainerId   Assigned trainer ID
     */
    public WorkoutClass(String type, String description, int trainerId) {
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }

    public int getWorkoutClassId() {
        return workoutClassId;
    }

    public void setWorkoutClassId(int workoutClassId) {
        this.workoutClassId = workoutClassId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Returns a string representation of the workout class.
     *
     * @return Formatted class details
     */
    @Override
    public String toString() {
        return "WorkoutClass{" +
                "workoutClassId=" + workoutClassId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", trainerId=" + trainerId +
                '}';
    }
}
