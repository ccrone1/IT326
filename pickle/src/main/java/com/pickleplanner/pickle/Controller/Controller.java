package com.pickleplanner.pickle.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Bracket.BracketHandler;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.EventHandler;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.Search_Filter.SearchHandler;
import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.User.UserHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
public class Controller {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private SearchHandler searchHandler;

    @Autowired
    private BracketHandler bracketHandler;

    @GetMapping("/accept")
    public String acceptInvitation(@RequestParam("eventId") Integer eventId, HttpServletResponse response) {
        return eventHandler.handleRequest(eventId, response, "accept");
    }

    @GetMapping("/decline")
    public String declineInvitation(@RequestParam("eventId") Integer eventId, HttpServletResponse response) {
        return eventHandler.handleRequest(eventId, response, "decline");
    }

    @PostMapping("/inviteUser")
    public String inviteUser(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send the request to the handler
        return userHandler.handleRequest("inviteUser", requestBody);
    }

    @PostMapping("/cancelInvite")
    public String cancelInvite(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send the request to the handler
        return userHandler.handleRequest("cancelInvite", requestBody);
    }

    @PostMapping("/events")
    public List<Event> getEvents() throws IOException {
        return eventHandler.handleRequest();
    }

    @PostMapping("/userEvents")
    public List<Event> getUserEvents(/* @RequestBody Map<String, Object> requestBody */) throws IOException {
        // String username = requestBody.get("username").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return new ArrayList<>();
        }

        List<Event> allEvents = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });

        // List<Event> filteredEvents = new ArrayList<Event>();
        // for (Event event : allEvents) {
        // // Example: Only include events with availability greater than 0
        // if (event.getOwner().getUsername().equals(username)) {
        // filteredEvents.add(event);
        // }
        // }
        return allEvents;
    }

    @PostMapping("/createEvent")
    public String createEvent(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send the request to handler
        return userHandler.handleRequest("createEvent", requestBody);
    }

    @PostMapping("/editEvent")
    public String editEvent(@RequestBody Map<String, Object> requestBody) {
        // Send the request to handler
        return eventHandler.handleRequest("editEvent", requestBody);
    }

    @PostMapping("/deleteEvent")
    public String deleteEvent(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send the request to handler
        return userHandler.handleRequest("deleteEvent", requestBody);
    }

    @PostMapping("/createProfile")
    public String createProfile(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("createProfile", requestBody);
    }

    @PostMapping("/editProfile")
    public String editProfile(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("editProfile", requestBody);
    }

    @PostMapping("/deleteProfile")
    public String deleteProfile(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("deleteProfile", requestBody);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("login", requestBody);
    }

    @PostMapping("/find-users")
    public User getUserByUsername(@RequestBody Map<String, Object> searchRequest) throws IOException {
        return userHandler.handleRequest(searchRequest);
    }

    @PostMapping("/joinEvent")
    public String joinEvent(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("joinEvent", requestBody);
    }

    @PostMapping("/leaveEvent")
    public String leaveEvent(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("leaveEvent", requestBody);
    }

    @PostMapping("/joinWaitlist")
    public String joinWaitlist(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("joinWaitlist", requestBody);
    }

    @PostMapping("/leaveWaitlist")
    public String leaveWaitlist(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("leaveWaitlist", requestBody);

    }

    @PostMapping("/kickUser")
    public String kickUser(@RequestBody Map<String, Object> requestBody) throws IOException {
        // Send request to handler
        return userHandler.handleRequest("kickUser", requestBody);

    }

    @PostMapping("/search")
    public List<Event> getFilteredEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
        return searchHandler.handleRequest(searchRequest);
    }

    @PostMapping("/generate_open")
    public List<Event> getOpenEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
        return eventHandler.handleRequest(searchRequest);
    }

    @PostMapping("/generate_bracket")
    public String generateBracketForEvent(@RequestBody Map<String, String> requestBody) throws IOException {
        String eventID = requestBody.get("eventID");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");

        if (!file.exists()) {
            throw new FileNotFoundException("Event file not found.");
        }

        // Read events data from the JSON file
        List<Event> events = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });

        // Find the event with the given eventID
        Event targetEvent = null;
        for (Event event : events) {
            if (event.getEventID().equals(eventID)) {
                targetEvent = event;
                break;
            }
        }

        if (targetEvent == null) {
            throw new IllegalArgumentException("Event with ID " + eventID + " not found.");
        }

        List<String> participantUsernames = new ArrayList<>();
        for (User participant : targetEvent.getParticipants()) {
            participantUsernames.add(participant.getUsername());
        }

        Bracket bracket = new Bracket();

        String bracketText = bracket.setParticipants(participantUsernames, targetEvent); // Set the participant
                                                                                         // usernames in the bracket

        return bracketText;
    }

}
