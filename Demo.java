public class Demo {
    public static void main(String[] args) {

        // --- Create a Trainer ---
        Trainer trainer = new Trainer(
                1,
                "fitMike",
                "$2a$10$someFakeHashedPassword",
                "mike@gym.com",
                "709-123-4567",
                "123 Strong St, Victoria, NL"
        );

        // --- Assign some classes to this trainer ---
        trainer.assignClass(101);
        trainer.assignClass(102);
        trainer.assignClass(103);

        // --- Try removing a class ---
        trainer.removeClass(102);

        // --- Output trainer info ---
        System.out.println("=== Trainer Details ===");
        System.out.println("Username: " + trainer.getUsername());
        System.out.println("Email: " + trainer.getEmail());
        System.out.println("Phone: " + trainer.getPhone());
        System.out.println("Address: " + trainer.getAddress());
        System.out.println("Assigned Classes: " + trainer.getAssignedClassIds());

        // --- Debug with toString ---
        System.out.println("\n[Trainer Object via toString()]");
        System.out.println(trainer);
    }
}
