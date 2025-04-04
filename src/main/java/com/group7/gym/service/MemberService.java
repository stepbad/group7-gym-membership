package com.group7.gym.service;

import com.group7.gym.dao.MemberDAO;
import com.group7.gym.models.Member;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing Member-related operations.
 */
public class MemberService {
    private MemberDAO memberDAO;

    /**
     * Constructs a MemberService with the given database connection.
     *
     * @param conn Active database connection
     */
    public MemberService(Connection conn) {
        this.memberDAO = new MemberDAO(conn);
    }

    /**
     * Registers a new member.
     *
     * @param member Member to register
     */
    public void registerMember(Member member) {
        try {
            memberDAO.addMember(member);
            System.out.println("Member registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering member: " + e.getMessage());
        }
    }

    /**
     * Lists all members.
     */
    public void listAllMembers() {
        try {
            List<Member> members = memberDAO.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members found.");
            } else {
                for (Member m : members) {
                    System.out.println(m);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving members: " + e.getMessage());
        }
    }

    /**
     * Views details of a member by ID.
     *
     * @param memberId ID of the member
     */
    public void viewMember(int memberId) {
        try {
            Member m = memberDAO.getMemberById(memberId);
            if (m != null) {
                System.out.println(m);
            } else {
                System.out.println("Member not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving member: " + e.getMessage());
        }
    }

    /**
     * Updates a member's information.
     *
     * @param member Member with updated data
     */
    public void updateMember(Member member) {
        try {
            memberDAO.updateMember(member);
            System.out.println("Member updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating member: " + e.getMessage());
        }
    }

    /**
     * Deletes a member by ID.
     *
     * @param memberId ID of the member to delete
     */
    public void deleteMember(int memberId) {
        try {
            memberDAO.deleteMember(memberId);
            System.out.println("Member deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
        }
    }
}
