package com.pickleplanner.pickle.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UserHandler {

    private final UserOperations userOperations;

    String userEmail;
    Integer eventID;
    String username;

    public UserHandler(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    public String handleRequest(String action, Map<String, Object> requestData) throws IOException {

        if (action == "inviteUser") {
            // Verify the data
            userEmail = (String) requestData.get("userEmail");
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }
            String id = (String) requestData.get("eventID");
            eventID = Integer.parseInt(id);
            if (eventID == null || eventID == 0) {
                return "Failed to send invitation. Event ID is missing or invalid.";
            }
            username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }

            // Proceed with the invitation logic
            return userOperations.sendInvitation(userEmail, eventID, username);
        }

        else if (action == "cancelInvite") {
            // Verify the Data
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }
            if (eventID == null || eventID == 0) {
                return "Failed to send invitation. Event ID is missing or invalid.";
            }
            if (userEmail == null || username.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }
            // Proceed with the invitation logic
            return userOperations.cancelInvitation(userEmail, eventID, username);
        }

        else if (action == "createEvent") {
            return userOperations.createEvent(requestData);
        }

        else if (action == "deleteEvent") {
            return userOperations.deleteEvent(requestData);
        }

        else if (action == "deleteProfile") {
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }
            return userOperations.deleteProfile(requestData);
        }

        else if (action == "createProfile") {
            return userOperations.createProfile(requestData);
        }

        else if (action == "joinWaitlist") {// Join waitlist
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Invalid userName";
            }
            String eventId = (String) requestData.get("eventId");
            if (eventId == null || eventId.isEmpty()) {
                return "Invalid event ID";
            }
            return userOperations.joinWaitlist(eventId, username);
        }

        else if (action == "leaveWaitlist") {// leave waitlist
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Invalid userName";
            }
            String eventId = (String) requestData.get("eventId");
            if (eventId == null || eventId.isEmpty()) {
                return "Invalid event ID";
            }
            return userOperations.leaveWaitlist(eventId, username);
        }

        else if (action == "joinEvent") {// joinEvent
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Invalid userName";
            }
            String eventId = (String) requestData.get("eventId");
            if (eventId == null || eventId.isEmpty()) {
                return "Invalid event ID";
            }
            return userOperations.joinEvent(requestData);
        }

        else if (action == "leaveEvent") {// LeaveEvent
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Invalid userName";
            }
            String eventId = (String) requestData.get("eventId");
            if (eventId == null || eventId.isEmpty()) {
                return "Invalid event ID";
            }
            return userOperations.LeaveEvent(username, eventId);
        }

        else if (action == "kickUser") {// LeaveEvent
            String username = (String) requestData.get("username");
            if (username == null || username.isEmpty()) {
                return "Invalid userName";
            }
            String eventId = (String) requestData.get("eventId");
            if (eventId == null || eventId.isEmpty()) {
                return "Invalid event ID";
            }
            return userOperations.kickFromEvent(username, eventId);
        }

        else if (action == "addFollower") {
            return userOperations.addFollower(requestData);
        }

        else if (action == "removeFollower") {
            return userOperations.removeFollower(requestData);
        }

        else {
            return "Invalid Request";
        }
    }

    public List<User> handleRequest2(@RequestBody Map<String, Object> requestData) throws IOException {
        return userOperations.displayFollower(requestData);
    }

    public User handleRequest(@RequestBody Map<String, Object> searchRequest) throws IOException {
        String username = (String) searchRequest.get("username");
        return userOperations.searchUser(username);
    }

    // Other methods for handling user-related actions
}
