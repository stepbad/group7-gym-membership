package com.group7.gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.group7.gym.models.Membership;

/**
 * DAO class for managing Membership-related database operations
 */
public class MembershipDAO {

    private static final Logger logger = Logger.getLogger(MembershipDAO.class.getName());
    private final String url = "jdbc:postgresql://localhost:5432/gym_management";
    private final String user = "postgres";
    private final String password = "your_password";

    /**
     * Inserts a new membership record into the database.
     *
     * @param membership Membership object to add
     * @return true if successful, false otherwise
     */
    public boolean addMembership(Membership membership) {
        String sql = "INSERT INTO memberships (membership_type, membership_description, membership_cost, member_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMemberId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Membership added successfully.");
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error adding membership: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves a membership by its unique ID.
     *
     * @param membershipId ID of the membership
     * @return Membership object or null if not found
     */
    public Membership getMembershipById(int membershipId) {
        String sql = "SELECT * FROM memberships WHERE membership_id = ?";
        Membership membership = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                membership = new Membership(
                        rs.getInt("membership_id"),
                        rs.getString("membership_type"),
                        rs.getString("membership_description"),
                        rs.getDouble("membership_cost"),
                        rs.getInt("member_id")
                );
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving membership: " + e.getMessage());
        }
        return membership;
    }

    /**
     * Retrieves all memberships from the database.
     *
     * @return List of Membership objects
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Membership membership = new Membership(
                        rs.getInt("membership_id"),
                        rs.getString("membership_type"),
                        rs.getString("membership_description"),
                        rs.getDouble("membership_cost"),
                        rs.getInt("member_id")
                );
                memberships.add(membership);
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving all memberships: " + e.getMessage());
        }
        return memberships;
    }

    /**
     * Updates an existing membership record in the database.
     *
     * @param membership Membership object with updated values
     * @return true if successful, false otherwise
     */
    public boolean updateMembership(Membership membership) {
        String sql = "UPDATE memberships SET membership_type = ?, membership_description = ?, membership_cost = ? WHERE membership_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMembershipId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Membership updated successfully.");
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error updating membership: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a membership from the database.
     *
     * @param membershipId ID of the membership to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteMembership(int membershipId) {
        String sql = "DELETE FROM memberships WHERE membership_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membershipId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Membership deleted successfully.");
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error deleting membership: " + e.getMessage());
        }
        return false;
    }

    /**
     * Calculates the total revenue generated from all memberships.
     *
     * @return Total revenue as a double
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(membership_cost) AS total_revenue FROM memberships";
        double totalRevenue = 0.0;

        try (Connection conn = DriverManager.getConnection(url, user, password);
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
}
