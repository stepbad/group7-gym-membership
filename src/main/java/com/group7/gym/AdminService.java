package com.group7.gym;

import java.util.List;
import java.util.logging.Logger;

public class AdminService {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());
    private AdminDAO adminDAO;
    
    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    public Admin getAdminById(int adminId) {
        Admin admin = adminDAO.getAdminById(adminId);
        if (admin == null) {
            logger.warning("Error: Admin not found with ID " + adminId);
        }
        return admin;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = adminDAO.getAllAdmins();
        if (admins.isEmpty()) {
            logger.info("No admins found.");
        }
        return admins;
    }

    public boolean addAdmin(Admin admin) {
        if (admin.getUserId() <= 0) {
            logger.warning("Error: Invalid user ID.");
            return false;
        }
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            logger.warning("Error: Invalid username.");
            return false;
        }

        boolean result = adminDAO.addAdmin(admin);
        if (result) {
            logger.info("Admin added successfully.");
        }
        return result;
    }

    public boolean updateAdmin(Admin admin) {
        if (admin.getUserId() <= 0) {
            logger.warning("Error: Invalid user ID.");
            return false;
        }
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            logger.warning("Error: Invalid username.");
            return false;
        }
        if (adminDAO.getAdminById(admin.getUserId()) == null) {
            logger.warning("Error: Admin not found with ID " + admin.getUserId());
            return false;
        }

        boolean result = adminDAO.updateAdmin(admin);
        if (result) {
            logger.info("Admin updated successfully.");
        }
        return result;
    }

    public boolean deleteAdmin(int adminId) {
        if (adminDAO.getAdminById(adminId) == null) {
            logger.warning("Error: Admin not found. Cannot delete.");
            return false;
        }
        boolean result = adminDAO.deleteAdmin(adminId);
        if (result) {
            logger.info("Admin deleted successfully.");
        }
        return result;
    }
    
}
