public class Invitation {
    private String status;

    // Constructor
    public Invitation(String status) {
        this.status = status;
    }

    // Getter and setter methods
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to update invitation status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Method to accept the invitation
    public void acceptInvitation() {
        this.status = "Accepted";
    }

    // Method to decline the invitation
    public void declineInvitation() {
        this.status = "Declined";
    }

    // Method to cancel the invitation
    public void cancelInvitation() {
        this.status = "Cancelled";
    }

 

}
