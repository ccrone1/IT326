public class User {
    private String username;
    private String password;
    private Profile profile;

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void joinWaitlist(Event event) {
        Waitlist.addToWaitlist(this, event);
    }

    public void leaveWaitlist(Event event) {
        Waitlist.removeFromWaitlist(this, event);
    }

    public void createEvent() {
        Event.createEvent();
    }

    public void deleteEvent(User user, Event event) {
        Event.deleteEvent(event);
    }

    public void createProfile(String firstName, String lastName, String email, String skillLevel) {
        this.profile = new Profile(firstName, lastName, email, skillLevel);
    }

    public void deleteProfile() {
        this.profile = null;
    }

    public void sendInvitation(User recipient, Event event) {
        Invitation.sendInvitation(recipient, event);
    }

    public void cancelInvitation(User recipient, Event event) {
        Invitation.cancelInvitation(recipient, event);
    }

}
