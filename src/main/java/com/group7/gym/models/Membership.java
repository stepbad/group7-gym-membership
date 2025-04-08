package com.group7.gym.models;

import java.time.LocalDate;

/**
 * Represents a gym membership with cost, type, duration, available credits, and associated member.
 */
public class Membership {

    /** Unique ID for the membership record. */
    private int membershipId;

    /** Type of the membership (e.g., Basic Day Pass, Gold Package). */
    private String membershipType;

    /** Description of membership benefits. */
    private String membershipDescription;

    /** Cost of the membership. */
    private double membershipCost;

    /** ID of the member who owns this membership. */
    private int memberId;

    /** Start date of the membership period. */
    private LocalDate startDate;

    /** End date of the membership period. */
    private LocalDate endDate;

    /** Number of available credits (used for classes, spa, guest passes, pool). */
    private int availableCredits;

    /**
     * Default constructor.
     */
    public Membership() {
    }

    /**
     * Full constructor for creating a membership object with full duration and credit support.
     *
     * @param membershipId          Unique membership ID
     * @param membershipType        Type of membership (e.g., Gold Package)
     * @param membershipDescription Description of benefits/features
     * @param membershipCost        Cost of the membership
     * @param memberId              ID of the associated member
     * @param startDate             Start date of membership
     * @param endDate               End date of membership
     * @param availableCredits      Number of available credits
     */
    public Membership(int membershipId, String membershipType, String membershipDescription,
                      double membershipCost, int memberId, LocalDate startDate,
                      LocalDate endDate, int availableCredits) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableCredits = availableCredits;
    }

    /**
     * Constructor without dates and credits.
     *
     * @param membershipId          Membership ID
     * @param membershipType        Membership type
     * @param membershipDescription Description of benefits
     * @param membershipCost        Cost of membership
     * @param memberId              Member ID
     */
    public Membership(int membershipId, String membershipType, String membershipDescription,
                      double membershipCost, int memberId) {
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

    public int getAvailableCredits() {
        return availableCredits;
    }

    public void setAvailableCredits(int availableCredits) {
        this.availableCredits = availableCredits;
    }

    public boolean hasAccessToCredits() {
        return membershipType != null &&
               (membershipType.equalsIgnoreCase("Gold") ||
                membershipType.equalsIgnoreCase("Platinum") ||
                membershipType.equalsIgnoreCase("Gold Package") ||
                membershipType.equalsIgnoreCase("Platinum Package"));
    }

    /**
     * Attempts to consume one credit if available.
     *
     * @return true if credit was used, false if none were available
     */
    public boolean useCredit() {
        if (availableCredits > 0) {
            availableCredits--;
            return true;
        }
        return false;
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
                ", memberId=" + memberId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", availableCredits=" + availableCredits +
                '}';
    }
}
