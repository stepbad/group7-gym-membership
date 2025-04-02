package src.main.java.com.group7.gym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO class for managing Membership-related database operations
 */
public class MembershipDAO {

    private static final Logger logger = Logger.getLogger(MembershipDAO.class.getName());
    private final String url = "jdbc:postgresql://localhost:5432/gym_management";
    private final String user = "postgres";
    private final String password = "your_password"; // Replace with your actual password

    // Add Membership
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

    // Get Membership by ID
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

    // Get All Memberships
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

    // Update Membership
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

    // Delete Membership
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

    // Get Total Revenue
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
