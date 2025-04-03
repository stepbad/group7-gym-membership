package com.group7.gym.service;

import java.util.List;
import java.util.logging.Logger;

import com.group7.gym.dao.MembershipDAO;
import com.group7.gym.models.Membership;

/**
 * Service class for managing business logic related to memberships.
 */
public class MembershipService {

    private static final Logger logger = Logger.getLogger(MembershipService.class.getName());
    private MembershipDAO membershipDAO;

    /**
     * Constructs the MembershipService and initializes its DAO.
     */
    public MembershipService() {
        this.membershipDAO = new MembershipDAO();
    }

    /**
     * Adds a new membership after validating cost and member ID.
     *
     * @param membership Membership to add
     * @return true if added successfully, false otherwise
     */
    public boolean addMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Error: Membership cost must be greater than 0.");
            return false;
        }
        if (membership.getMemberId() <= 0) {
            logger.warning("Error: Invalid member ID.");
            return false;
        }
        boolean result = membershipDAO.addMembership(membership);
        if (result) {
            logger.info("Membership added successfully.");
        }
        return result;
    }

    /**
     * Retrieves a membership by ID.
     *
     * @param membershipId Membership ID to search
     * @return Membership object or null if not found
     */
    public Membership getMembershipById(int membershipId) {
        Membership membership = membershipDAO.getMembershipById(membershipId);
        if (membership == null) {
            logger.warning("Error: Membership not found with ID " + membershipId);
        }
        return membership;
    }

    /**
     * Retrieves all memberships in the system.
     *
     * @return List of Membership objects
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        if (memberships.isEmpty()) {
            logger.info("No memberships found.");
        }
        return memberships;
    }

    /**
     * Updates an existing membership after validation.
     *
     * @param membership Updated membership object
     * @return true if successful, false otherwise
     */
    public boolean updateMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Error: Membership cost must be greater than 0.");
            return false;
        }
        if (membershipDAO.getMembershipById(membership.getMembershipId()) == null) {
            logger.warning("Error: Membership not found with ID " + membership.getMembershipId());
            return false;
        }
        boolean result = membershipDAO.updateMembership(membership);
        if (result) {
            logger.info("Membership updated successfully.");
        }
        return result;
    }

    /**
     * Deletes a membership by ID if it exists.
     *
     * @param membershipId ID of the membership to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteMembership(int membershipId) {
        if (membershipDAO.getMembershipById(membershipId) == null) {
            logger.warning("Error: Membership not found. Cannot delete.");
            return false;
        }
        boolean result = membershipDAO.deleteMembership(membershipId);
        if (result) {
            logger.info("Membership deleted successfully.");
        }
        return result;
    }

    /**
     * Returns the total revenue generated from all memberships.
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
