package com.group7.gym.models;

/**
 * Represents a gym member, inheriting from User.
 * Members have a membership ID and track total expenses.
 */
public class Member extends User {

    /** ID of the associated membership type (e.g., Basic, Standard, Premium). */
    private int membershipId;

    /** Total expenses accumulated by the member. */
    private double totalMembershipExpenses;

    /**
     * Default constructor.
     */
    public Member() {
    }

    /**
     * Full constructor for creating a Member object.
     *
     * @param userId                  Unique user ID
     * @param username                Member's username
     * @param passwordHash            Hashed password
     * @param email                   Member's email address
     * @param phone                   Member's phone number
     * @param address                 Member's physical address
     * @param membershipId            ID of the associated membership plan
     * @param totalMembershipExpenses Total expenses incurred by the member
     */
    public Member(int userId, String username, String passwordHash, String email, String phone, String address,
                  int membershipId, double totalMembershipExpenses) {
        super(userId, username, passwordHash, email, phone, address, "member");
        this.membershipId = membershipId;
        this.totalMembershipExpenses = totalMembershipExpenses;
    }

    /**
     * Gets the member's membership ID.
     *
     * @return Membership ID
     */
    public int getMembershipId() {
        return membershipId;
    }

    /**
     * Sets the membership ID.
     *
     * @param membershipId New membership ID
     */
    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * Gets the total membership expenses.
     *
     * @return Total amount spent
     */
    public double getTotalMembershipExpenses() {
        return totalMembershipExpenses;
    }

    /**
     * Sets the total membership expenses.
     *
     * @param totalMembershipExpenses Total expenses value
     */
    public void setTotalMembershipExpenses(double totalMembershipExpenses) {
        this.totalMembershipExpenses = totalMembershipExpenses;
    }

    /**
     * Adds a charge to the member’s total expenses.
     *
     * @param amount Amount to add
     */
    public void addMembershipExpense(double amount) {
        this.totalMembershipExpenses += amount;
    }

    /**
     * Returns a string representation of the member object.
     *
     * @return String with member details
     */
    @Override
    public String toString() {
        return "Member{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", membershipId=" + membershipId +
                ", totalMembershipExpenses=" + totalMembershipExpenses +
                '}';
    }
}
