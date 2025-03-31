/**
 * Member class - inherits from User and adds membership-specific attributes
 */
public class Member extends User {

    private int membershipId;
    private double totalMembershipExpenses;

    // Constructors

    public Member() {
        // Default constructor
    }

    public Member(int userId, String username, String passwordHash, String email, String phone, String address,
                  int membershipId, double totalMembershipExpenses) {
        super(userId, username, passwordHash, email, phone, address, "member"); // Role set to 'member'
        this.membershipId = membershipId;
        this.totalMembershipExpenses = totalMembershipExpenses;
    }

    // Getters and Setters

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public double getTotalMembershipExpenses() {
        return totalMembershipExpenses;
    }

    public void setTotalMembershipExpenses(double totalMembershipExpenses) {
        this.totalMembershipExpenses = totalMembershipExpenses;
    }

    // Method to add membership expenses

    public void addMembershipExpense(double amount) {
        this.totalMembershipExpenses += amount;
    }

    // toString Method

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
