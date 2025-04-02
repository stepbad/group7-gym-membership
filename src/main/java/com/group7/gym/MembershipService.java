package src.main.java.com.group7.gym;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing memberships
 */
public class MembershipService {

    private static final Logger logger = Logger.getLogger(MembershipService.class.getName());
    private MembershipDAO membershipDAO;

    // Constructor
    public MembershipService() {
        this.membershipDAO = new MembershipDAO();
    }

    // Add Membership
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

    // Get Membership by ID
    public Membership getMembershipById(int membershipId) {
        Membership membership = membershipDAO.getMembershipById(membershipId);
        if (membership == null) {
            logger.warning("Error: Membership not found with ID " + membershipId);
        }
        return membership;
    }

    // Get All Memberships
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        if (memberships.isEmpty()) {
            logger.info("No memberships found.");
        }
        return memberships;
    }

    // Update Membership
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

    // Delete Membership
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

    // Get Total Revenue
    public double getTotalRevenue() {
        double totalRevenue = membershipDAO.getTotalRevenue();
        if (totalRevenue == 0) {
            logger.info("No revenue generated yet.");
        }
        return totalRevenue;
    }
}
