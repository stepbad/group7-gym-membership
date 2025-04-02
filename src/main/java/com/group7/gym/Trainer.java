package src.main.java.com.group7.gym;

import java.util.ArrayList;
import java.util.List;

/**
 * Trainer subclass of User – represents a gym trainer.
 */
public class Trainer extends User {

    //  List of IDs for workout classes this trainer is assigned to
    private List<Integer> assignedClassIds;

    public Trainer(int userId, String username, String passwordHash, String email, String phone, String address) {
        super(userId, username, passwordHash, email, phone, address, "Trainer");
        this.assignedClassIds = new ArrayList<>();
    }

    // --- Methods ---

    public void assignClass(int classId) {
        if (!assignedClassIds.contains(classId)) {
            assignedClassIds.add(classId);
        }
    }

    public void removeClass(int classId) {
        assignedClassIds.remove(Integer.valueOf(classId));
    }

    public List<Integer> getAssignedClassIds() {
        return assignedClassIds;
    }

    public void setAssignedClassIds(List<Integer> assignedClassIds) {
        this.assignedClassIds = assignedClassIds;
    }

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
