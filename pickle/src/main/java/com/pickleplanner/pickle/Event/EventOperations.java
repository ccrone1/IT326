
package com.pickleplanner.pickle.Event;

import com.pickleplanner.pickle.User.User;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class EventOperations {

    public void addToWaitlist(Event event, String user) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User targetUser = targetEvent.getWaitlist().getWaitList().stream()
                    .filter(u -> u.getUsername().equals(user))
                    .findFirst()
                    .orElse(null);

            if (targetUser != null) {
                targetEvent.getWaitlist().getWaitList().add(targetUser);

            }
        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        // list.getWaitList().add(user);
    }

    public void removeFromWaitlist(Event event, String user) {
        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getWaitlist().getWaitList().stream()
                    .filter(u -> u.getUsername().equals(user))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {
                targetEvent.getWaitlist().getWaitList().remove(userToRemove);

            }
        }

        // Convert back to JSON if needed
        String updatedJson = new Gson().toJson(events);
        // need to push
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

    // Map the objects from the event_output.json file and return them in a List
    public List<Event> retrieveEvents() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return new ArrayList<>();
        }

        List<Event> events = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });
        return events;
    }

    public String acceptInvitation(Integer eventId, HttpServletResponse response) {
        String redirectUrl = "/event.html?action=accept&eventId=" + eventId;
        response.setHeader("Location", redirectUrl);
        // Set the status code to indicate a redirect
        response.setStatus(HttpServletResponse.SC_FOUND); // or HttpServletResponse.SC_MOVED_PERMANENTLY
        return null; // Return null to prevent rendering any view
    }

    public String declineInvitation(Integer eventId, HttpServletResponse response) {
        // Set the Location header for redirection with the action parameter
        response.setHeader("Location", "/event.html?action=decline");
        // Set the status code to indicate a redirect
        response.setStatus(HttpServletResponse.SC_FOUND); // or HttpServletResponse.SC_MOVED_PERMANENTLY
        return null; // Return null to prevent rendering any view
    }

    public String editEvent(Map<String, Object> requestData) {
        return "";
    }

    public List<Event> generateOpenEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");
        List<Event> openEvents = new ArrayList<>();

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return openEvents;
        }

        // Read events data from the JSON file
        List<Event> events = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });

        for (Event event : events) {
            // Check if the keyword exists in the event location
            if (event.getAvailability() > event.getParticipants().size()) {
                openEvents.add(event);
            }
        }

        return openEvents;
    }

}
