package com.pickleplanner.pickle.Event;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class EventHandler {

    private final EventOperations eventOperations;

    String userEmail;

    public EventHandler(EventOperations eventOperations) {
        this.eventOperations = eventOperations;
    }

    public String handleRequest(String action, Map<String, Object> requestData) {
        if (action == "editEvent") {
            return eventOperations.editEvent(requestData);
        }

        else {
            return "Invalid Request";
        }
    }

    public List<Event> handleRequest() throws IOException {
        return eventOperations.retrieveEvents();
    }

    public String handleRequest(@RequestParam("eventId") Integer eventId,
            HttpServletResponse response, String operation) {
        if (operation == "accept") {
            return eventOperations.acceptInvitation(eventId, response);
        } else if (operation == "decline") {
            return eventOperations.declineInvitation(eventId, response);
        } else {
            return "Invalid Request";
        }
    }

    public List<Event> handleRequest(@RequestBody Map<String, Object> searchRequest) throws IOException {
        return eventOperations.generateOpenEvents(searchRequest);
    }

    // Methods to handle event-related requests and interact with EventOperations
}
