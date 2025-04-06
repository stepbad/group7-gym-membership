package com.group7.gym.models;
import java.time.LocalDate;
/**
 * Represents a gym membership with cost, type, and association to a member.
 */
public class Membership {

    private int membershipId;
    private int memberId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Default constructor.
     */
    public Membership() {
        // Default constructor
    }

    // Constructor to initialize all fields
    public Membership(int membershipId, int memberId, String membershipType, String membershipDescription,
                      double membershipCost, LocalDate startDate, LocalDate endDate) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Full constructor for creating a membership object.
     *
     * @param membershipId          Unique membership ID
     * @param membershipType        Type of membership (e.g., Basic, Premium)
     * @param membershipDescription Description of benefits/features
     * @param membershipCost        Monthly cost of the membership
     * @param memberId              ID of the member who holds this membership
     */
    public Membership(int membershipId, String membershipType, String membershipDescription, double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }


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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    /**
     * Returns a formatted string describing the membership.
     *
     * @return Membership summary string
     */
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", startDate=" + startDate +  // Include startDate in toString
                ", endDate=" + endDate +      // Include endDate in toString
                ", memberId=" + memberId +
                '}';
    }
}
