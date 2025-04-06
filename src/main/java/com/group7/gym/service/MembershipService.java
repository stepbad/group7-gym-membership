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

    public boolean addMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Error: Membership cost must be greater than 0.");
            return false;
        }
        // Validate member ID
        if (membership.getMemberId() <= 0) {
            logger.warning("Error: Invalid member ID.");
            return false;
        }

        // Validate membership type (new validation step)
        if (membership.getMembershipType() == null || membership.getMembershipType().trim().isEmpty()) {
            logger.warning("Error: Membership type cannot be null or empty.");
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

    public Membership getMembershipById(int membershipId) {
        Membership membership = membershipDAO.getMembershipById(membershipId);
        if (membership == null) {
            logger.warning("Error: Membership not found with ID " + membershipId);
        }
        return membership;
    }

    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        if (memberships.isEmpty()) {
            logger.info("No memberships found.");
        }
        return memberships;
    }

    public boolean updateMembership(Membership membership) {
        if (membership.getMembershipCost() <= 0) {
            logger.warning("Error: Membership cost must be greater than 0.");
            return false;
        }
        // Validate membership type
        if (membership.getMembershipType() == null || membership.getMembershipType().trim().isEmpty()) {
            logger.warning("Error: Membership type cannot be null or empty.");
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

    public double getTotalRevenue() {
        double totalRevenue = membershipDAO.getTotalRevenue();
        if (totalRevenue == 0) {
            logger.info("No revenue generated yet.");
        }
        return totalRevenue;
    }

    /**
     * Retrieves memberships associated with a specific member ID.
     *
     * @param memberId ID of the member
     * @return List of Memberships belonging to the member
     */
    public List<Membership> getMembershipsByMemberId(int memberId) {
        return membershipDAO.getMembershipsByMemberId(memberId);
    }
}
