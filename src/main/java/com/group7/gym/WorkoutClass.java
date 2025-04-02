package src.main.java.com.group7.gym;

public class WorkoutClass {
    private int workoutClassId;
    private String type;
    private String description;
    private int trainerId;

    public WorkoutClass(int workoutClassId, String type, String description, int trainerId) {
        this.workoutClassId = workoutClassId;
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }

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
