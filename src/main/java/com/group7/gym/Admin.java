package com.group7.gym;
    import java.util.ArrayList;
    import java.util.List;

    public class Admin extends User {
        
        public Admin(int i, String string, String string2, String string3, String string4, String string5) {
            super();
            this.role = "Admin";
        }

        public Admin(int userId, String passwordHash, String email, String phone, String address) {
            super(userId, username, passwordHash, email, phone, address, "Admin");
        }

        private List<User> userList = new ArrayList<>();

        private List<Membership> membershipList = new ArrayList<>();

        public void viewAllUsers() {
            System.out.println("--- All Users ---");
            for (User user : userList) {
                System.out.println(user);
            }
        }

        public void deleteUser(int userId) {
            userList.removeIf(user -> user.getUserId() == userId);
            System.out.println("User with ID " + userId + " deleted.");
        }

        public void viewMembershipsAndRevenue() {
            System.out.println("--- Gym Memberships ---");
            double totalRevenue = 0;
            for (Membership membership : membershipList) {
                System.out.println(membership);
                totalRevenue += membership.getMembershipCost();
            }
            System.out.println("Total Revenue: $" + totalRevenue);
        }

        @Override
        public String toString() {
            return "Admin{" +
                    "userId=" + userId +
                    ", username=" + username + '\'' +
                    ", email=" + email + '\'' +
                    ", phone=" + phone + '\'' +
                    ", address=" + address + '\'' +
                    ", role=" + role +
                    '}';
        }

        public void addUser(User user){
            userList.add(user);
        }

        public void addMembership(Membership membership){
            membershipList.add(membership);
        }
    }


