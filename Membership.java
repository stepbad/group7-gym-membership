/**
 * Membership class - Represents a gym membership with relevant details
 */
public class Membership {

    private int membershipId;
    private String membershipType;  // e.g., "Standard", "Premium"
    private String membershipDescription;  // Description of the membership
    private double membershipCost;  // Cost of the membership
    private int memberId;  // ID of the member who holds this membership

    // Constructors

    public Membership() {
        // Default constructor
    }

    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    // Getters and Setters

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipDescription() {
        return membershipDescription;
    }

    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    public double getMembershipCost() {
        return membershipCost;
    }

    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    // toString Method
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", memberId=" + memberId +
                '}';
    }
}
