package com.group7.gym.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing a workout class in the gym.
 */
public class WorkoutClass {

    private int workoutClassId;
    private String type;
    private String description;
    private int trainerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate classDate;

    public WorkoutClass(int workoutClassId, String type, String description, int trainerId) {
        this.workoutClassId = workoutClassId;
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }
    /**
     * Full constructor for WorkoutClass including ID and scheduling.
     *
     * @param workoutClassId the ID of the class
     * @param type the type of workout (e.g. Yoga, Cardio)
     * @param description details about the class
     * @param trainerId the trainer assigned to the class
     * @param startTime the start time of the class
     * @param endTime the end time of the class
     * @param classDate the date of the class
     */
    public WorkoutClass(int workoutClassId, String type, String description, int trainerId,
                        LocalTime startTime, LocalTime endTime, LocalDate classDate) {
        this.workoutClassId = workoutClassId;
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classDate = classDate;
    }

    /**
     * Constructor for WorkoutClass without ID (used for creation).
     *
     * @param type the type of workout
     * @param description details about the class
     * @param trainerId the trainer assigned to the class
     * @param startTime the start time of the class
     * @param endTime the end time of the class
     * @param classDate the date of the class
     */
    public WorkoutClass(String type, String description, int trainerId,
                        LocalTime startTime, LocalTime endTime, LocalDate classDate) {
        this(-1, type, description, trainerId, startTime, endTime, classDate);
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

@Override
public String toString() {
    return String.format(
        "\n[Class ID: %d]\n" +
        "Type       : %s\n" +
        "Description: %s\n" +
        "Trainer ID : %d\n" +
        "Date       : %s\n" +
        "Time       : %s - %s\n",
        workoutClassId,
        type,
        description,
        trainerId,
        classDate.format(dateFormat),
        startTime.format(timeFormat),
        endTime.format(timeFormat)
    );
}

}
