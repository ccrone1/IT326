package com.pickleplanner.pickle.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {

    private final UserOperations userOperations;

    String userEmail;

    @Autowired
    public UserHandler(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    public String handleRequest(String action, Map<String, Object> requestData) {

        if (action == "inviteUser") {
            // Verify the data
            userEmail = (String) requestData.get("userEmail");
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }

            // Proceed with the invitation logic
            return userOperations.sendInvitation(userEmail);
        }

        else if (action == "cancelInvite") {
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }

            // Proceed with the invitation logic
            return userOperations.cancelInvitation(userEmail);
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
            return userOperations.deleteProfile(username);
        }

        else if (action == "createProfile") {
            return userOperations.createProfile(requestData);
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

    // Other methods for handling user-related actions
}
