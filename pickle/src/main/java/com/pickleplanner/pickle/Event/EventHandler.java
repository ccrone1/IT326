package com.pickleplanner.pickle.Event;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    private final EventOperations eventOperations;

    String userEmail;

    @Autowired
    public EventHandler(EventOperations eventOperations) {
        this.eventOperations = eventOperations;
    }

    public String handleRequest(String action, Map<String, Object> requestData) {

        if (action == "inviteUser") {
            // Verify the data
            userEmail = (String) requestData.get("userEmail");
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }

            // Proceed with the invitation logic
            return eventOperations.sendInvitation(userEmail);
        }

        else if (action == "cancelInvite") {
            if (userEmail == null || userEmail.isEmpty()) {
                return "Failed to send invitation. User email is missing or invalid.";
            }

            // Proceed with the invitation logic
            return eventOperations.cancelInvitation(userEmail);
        } else {
            return "Invalid Request";
        }
    }

    // Methods to handle event-related requests and interact with EventOperations
}
