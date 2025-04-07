package com.group7.gym.service;

import java.util.List;
import java.util.logging.Logger;
import com.group7.gym.DatabaseConnection;
import com.group7.gym.dao.MembershipDAO;
import com.group7.gym.models.Membership;
import java.sql.*;
import java.time.LocalDate;


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
        String sql = "SELECT * FROM memberships WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Check if start_date and end_date are not null before converting
                Date startDateSQL = rs.getDate("start_date");
                Date endDateSQL = rs.getDate("end_date");

                LocalDate startDate = (startDateSQL != null) ? startDateSQL.toLocalDate() : null;
                LocalDate endDate = (endDateSQL != null) ? endDateSQL.toLocalDate() : null;

                // If startDate or endDate is null, handle gracefully
                if (startDate == null || endDate == null) {
                    logger.severe("Start date or end date is null for membership ID " + membershipId);
                }

                // Create Membership object
                return new Membership(
                        rs.getInt("membership_id"),
                        rs.getString("membership_type"),
                        rs.getString("membership_description"),
                        rs.getDouble("membership_cost"),
                        rs.getInt("member_id"),
                        startDate,
                        endDate
                );
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving membership: " + e.getMessage());
        }
        return null;
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

        String sql = "UPDATE memberships SET membership_type = ?, membership_description = ?, membership_cost = ?, end_date = ? WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setDate(4, Date.valueOf(membership.getEndDate()));   // Update endDate here
            stmt.setInt(6, membership.getMembershipId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.severe("Error updating membership: " + e.getMessage());
            return false;
        }

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
