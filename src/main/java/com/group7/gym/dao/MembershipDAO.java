package com.group7.gym.dao;

import com.group7.gym.DatabaseConnection;
import com.group7.gym.models.Membership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO class for managing Membership-related database operations.
 */
public class MembershipDAO {

    private static final Logger logger = Logger.getLogger(MembershipDAO.class.getName());

    /**
     * Adds a new membership to the database.
     *
     * @param membership Membership object to add
     * @return true if successful, false otherwise
     */
    public boolean addMembership(Membership membership) {
        if (membership.getMembershipType() == null || membership.getMembershipType().isEmpty()) {
            logger.severe("Membership type cannot be null or empty.");
            return false;
        }

        String sql = "INSERT INTO memberships (membership_type, membership_description, membership_cost, member_id, start_date, end_date, available_credits) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMemberId());
            stmt.setDate(5, Date.valueOf(membership.getStartDate()));
            stmt.setDate(6, Date.valueOf(membership.getEndDate()));
            stmt.setInt(7, membership.getAvailableCredits());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error adding membership: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a membership by its ID.
     *
     * @param membershipId ID of the membership
     * @return Membership object or null if not found
     */
    public Membership getMembershipById(int membershipId) {
        String sql = "SELECT * FROM memberships WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractMembership(rs);
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving membership: " + e.getMessage());
        }

        return null;
    }

    /**
     * Retrieves the number of credits for a given member.
     *
     * @param memberId ID of the member
     * @return Number of credits, or -1 if not found/error
     */
    public int getCreditsByMemberId(int memberId) {
        String sql = "SELECT credits FROM memberships WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("credits");
            }
        } catch (SQLException e) {
            logger.warning("Error retrieving credits: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Deducts a specified number of credits from a member's account.
     *
     * @param memberId ID of the member
     * @param amount   Number of credits to deduct
     * @return true if successful, false otherwise
     */
    public boolean deductCredits(int memberId, int amount) {
        String sql = "UPDATE memberships SET credits = credits - ? WHERE member_id = ? AND credits >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setInt(2, memberId);
            stmt.setInt(3, amount);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.warning("Error deducting credits: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all memberships associated with a specific member.
     *
     * @param memberId ID of the member
     * @return List of Membership objects
     */
    public List<Membership> getMembershipsByMemberId(int memberId) {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(extractMembership(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving memberships by member ID: " + e.getMessage());
        }

        return memberships;
    }

    /**
     * Retrieves all current memberships.
     *
     * @return List of valid Membership objects
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE end_date >= CURRENT_DATE";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(extractMembership(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error retrieving memberships: " + e.getMessage());
        }

        return memberships;
    }

    /**
     * Updates a membership's details.
     *
     * @param membership Updated Membership object
     * @return true if update is successful, false otherwise
     */
    public boolean updateMembership(Membership membership) {
        if (membership.getMembershipType() == null || membership.getMembershipType().isEmpty()) {
            logger.severe("Membership type cannot be null or empty.");
            return false;
        }

        String sql = "UPDATE memberships SET membership_type = ?, membership_description = ?, membership_cost = ?, available_credits = ? WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getAvailableCredits());
            stmt.setInt(5, membership.getMembershipId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error updating membership: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a membership by ID.
     *
     * @param membershipId ID of the membership to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteMembership(int membershipId) {
        String sql = "DELETE FROM memberships WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membershipId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error deleting membership: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates only the available credits for a membership.
     *
     * @param membershipId ID of the membership
     * @param newCreditCount Updated credit value
     * @return true if updated successfully, false otherwise
     */
    public boolean updateAvailableCredits(int membershipId, int newCreditCount) {
        String sql = "UPDATE memberships SET available_credits = ? WHERE membership_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newCreditCount);
            stmt.setInt(2, membershipId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error updating available credits: " + e.getMessage());
            return false;
        }
    }
    

    /**
     * Returns total revenue collected from all memberships.
     *
     * @return Double value of total revenue
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(membership_cost) AS total_revenue FROM memberships";
        double totalRevenue = 0.0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }

        } catch (SQLException e) {
            logger.severe("Error calculating total revenue: " + e.getMessage());
        }

        return totalRevenue;
    }


    /**
     * Helper method to extract a Membership object from a ResultSet.
     *
     * @param rs The current result set
     * @return Populated Membership object
     * @throws SQLException if fields are invalid
     */
    private Membership extractMembership(ResultSet rs) throws SQLException {
        return new Membership(
                rs.getInt("membership_id"),
                rs.getString("membership_type"),
                rs.getString("membership_description"),
                rs.getDouble("membership_cost"),
                rs.getInt("member_id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getInt("available_credits")
        );
    }
}
