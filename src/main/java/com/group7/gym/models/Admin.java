package com.group7.gym.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an administrator user in the gym system.
 * Admins can view and manage users and memberships locally.
 */
public class Admin extends User {

    private List<User> userList = new ArrayList<>();
    private List<Membership> membershipList = new ArrayList<>();

    /**
     * Constructs a new Admin user.
     *
     * @param userId        Admin's unique ID
     * @param username      Username
     * @param passwordHash  Hashed password
     * @param email         Email address
     * @param phone         Phone number
     * @param address       Physical address
     */
    public Admin(int userId, String username, String passwordHash, String email, String phone, String address) {
        super(userId, username, passwordHash, email, phone, address, "Admin");
    }

    /**
     * Adds a user to the local admin-managed user list.
     *
     * @param user User to add
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Removes a user by ID from the local user list.
     *
     * @param userId ID of the user to remove
     */
    public void deleteUser(int userId) {
        userList.removeIf(user -> user.getUserId() == userId);
        System.out.println("User with ID " + userId + " deleted.");
    }

    /**
     * Displays all users from the local user list.
     */
    public void viewAllUsers() {
        System.out.println("--- All Users ---");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * Adds a membership record to the local list.
     *
     * @param membership Membership to add
     */
    public void addMembership(Membership membership) {
        membershipList.add(membership);
    }

    /**
     * Displays all memberships and total revenue from the local list.
     */
    public void viewMembershipsAndRevenue() {
        System.out.println("--- Gym Memberships ---");
        double totalRevenue = 0;
        for (Membership membership : membershipList) {
            System.out.println(membership);
            totalRevenue += membership.getMembershipCost();
        }
        System.out.println("Total Revenue: $" + totalRevenue);
    }

    /**
     * Returns a formatted string summary of the admin.
     *
     * @return Admin details
     */
    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + getUserId() +
                ", username=" + getUsername() +
                ", email=" + getEmail() +
                ", phone=" + getPhone() +
                ", address=" + getAddress() +
                ", role=" + getRole() +
                '}';
    }
}
