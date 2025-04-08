package com.group7.gym.service;

import com.group7.gym.dao.MembershipDAO;
import com.group7.gym.models.Membership;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service class that encapsulates the business logic related to gym memberships,
 * including credit handling and data validation.
 */
public class MembershipService {

    private static final Logger logger = Logger.getLogger(MembershipService.class.getName());
    private final MembershipDAO membershipDAO;

    /**
     * Constructs the MembershipService and initializes the underlying DAO.
     */
    public MembershipService() {
        this.membershipDAO = new MembershipDAO();
    }

    /**
     * Adds a new membership if valid.
     *
     * @param membership Membership object to be added
     * @return true if added successfully; false otherwise
     */
    public boolean addMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Membership cost must be greater than 0.");
            return false;
        }

        if (membership.getMemberId() <= 0) {
            logger.warning("Invalid member ID.");
            return false;
        }

        if (membership.getMembershipType() == null || membership.getMembershipType().trim().isEmpty()) {
            logger.warning("Membership type cannot be null or empty.");
            return false;
        }

        boolean result = membershipDAO.addMembership(membership);
        if (result) {
            logger.info("Membership added successfully.");
        } else {
            logger.warning("Failed to add membership.");
        }
        return result;
    }

    /**
     * Retrieves a membership by its unique ID.
     *
     * @param membershipId ID of the membership
     * @return Membership object or null if not found
     */
    public Membership getMembershipById(int membershipId) {
        return membershipDAO.getMembershipById(membershipId);
    }

    /**
     * Retrieves the most recent membership for a specific member.
     * Assumes each member has at most one active membership.
     *
     * @param memberId ID of the member
     * @return Membership object or null if not found
     */
    public Membership getCurrentMembershipByMemberId(int memberId) {
        List<Membership> memberships = membershipDAO.getMembershipsByMemberId(memberId);
        return (memberships.isEmpty()) ? null : memberships.get(0);
    }

    /**
     * Deducts one credit from a member’s membership if available.
     *
     * @param memberId ID of the member
     * @return true if a credit was used; false if insufficient credits or no membership
     */
    public boolean useCredit(int memberId) {
        Membership membership = getCurrentMembershipByMemberId(memberId);
        if (membership == null) {
            logger.warning("No membership found for member ID: " + memberId);
            return false;
        }

        if (membership.getAvailableCredits() <= 0) {
            logger.info("Not enough credits available.");
            return false;
        }

        membership.setAvailableCredits(membership.getAvailableCredits() - 1);
        return membershipDAO.updateAvailableCredits(membership.getMembershipId(), membership.getAvailableCredits());
    }

    /**
     * Updates the membership data (type, description, cost, and credits).
     *
     * @param membership Updated Membership object
     * @return true if update succeeds, false otherwise
     */
    public boolean updateMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Membership cost must be greater than 0.");
            return false;
        }

        if (membership.getMembershipType() == null || membership.getMembershipType().trim().isEmpty()) {
            logger.warning("Membership type cannot be null or empty.");
            return false;
        }

        boolean result = membershipDAO.updateMembership(membership);
        if (result) {
            logger.info("Membership updated.");
        }
        return result;
    }

    /**
     * Deletes a membership from the system.
     *
     * @param membershipId ID of the membership to delete
     * @return true if deletion was successful; false otherwise
     */
    public boolean deleteMembership(int membershipId) {
        Membership existing = membershipDAO.getMembershipById(membershipId);
        if (existing == null) {
            logger.warning("Membership not found. Cannot delete.");
            return false;
        }

        return membershipDAO.deleteMembership(membershipId);
    }

    /**
     * Returns a list of all currently active memberships.
     *
     * @return List of active Membership objects
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        if (memberships.isEmpty()) {
            logger.info("No memberships found.");
        }
        return memberships;
    }

    /**
     * Returns all memberships ever held by a specific member.
     *
     * @param memberId Member's unique ID
     * @return List of Memberships associated with the member
     */
    public List<Membership> getMembershipsByMemberId(int memberId) {
        return membershipDAO.getMembershipsByMemberId(memberId);
    }

    /**
     * Calculates the total revenue from all memberships.
     *
     * @return Total revenue as a double
     */
    public double getTotalRevenue() {
        double totalRevenue = membershipDAO.getTotalRevenue();
        if (totalRevenue == 0) {
            logger.info("No revenue generated yet.");
        }
        return totalRevenue;
    }
}
