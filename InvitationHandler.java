public class InvitationHandler {
    private final UserOperations userOperations;

    public InvitationHandler() {
        this.userOperations = new UserOperations();
    }

    public void createUser(String[] userData) {
        // Delegate to operations class to create a new user
    }

    // Other methods for handling user-related actions
}