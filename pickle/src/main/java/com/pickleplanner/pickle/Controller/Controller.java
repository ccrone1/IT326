package com.pickleplanner.pickle.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.EventHandler;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.User.UserHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;

@RestController
public class Controller {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private EventHandler eventHandler;

    @GetMapping("/accept")
    public String acceptInvitation(@RequestParam("eventId") Integer eventId, HttpServletResponse response) {
        return eventHandler.handleRequest(eventId, response, "accept");
    }

    @GetMapping("/decline")
    public String declineInvitation(@RequestParam("eventId") Integer eventId, HttpServletResponse response) {
        return eventHandler.handleRequest(eventId, response, "decline");
    }

    @PostMapping("/inviteUser")
    public String inviteUser(@RequestBody Map<String, Object> requestBody) {
        // Send the request to the handler
        return userHandler.handleRequest("inviteUser", requestBody);
    }

    @PostMapping("/cancelInvite")
    public String cancelInvite(@RequestBody Map<String, Object> requestBody) {
        // Send the request to the handler
        return userHandler.handleRequest("cancelInvite", requestBody);
    }

    @PostMapping("/events")
    public List<Event> getEvents() throws IOException {
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
    public String createEvent(@RequestBody Map<String, Object> requestBody) {
        // Send the request to handler
        return userHandler.handleRequest("createEvent", requestBody);
    }

    @PostMapping("/editEvent")
    public String editEvent(@RequestBody Map<String, Object> requestBody) {
        // Send the request to handler
        return eventHandler.handleRequest("editEvent", requestBody);
    }

    @PostMapping("/deleteEvent")
    public String deleteEvent(@RequestBody Map<String, Object> requestBody) {
        // Send the request to handler
        return userHandler.handleRequest("deleteEvent", requestBody);
    }

    @PostMapping("/createProfile")
    public String createProfile(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("createProfile", requestBody);
    }

    @PostMapping("/editProfile")
    public String editProfile(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("editProfile", requestBody);
    }


    @DeleteMapping("/deleteProfile")
    public String deleteProfile(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("deleteProfile", requestBody);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("login", requestBody);
    }

    @PostMapping("/find-users")
    public User getUserByUsername(@RequestBody Map<String, Object> searchRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("user_data.json");

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return null;
        }

        // Read user data from the JSON file
        User[] users = objectMapper.readValue(file, User[].class);

        String username = (String) searchRequest.get("username");

        // Find user by username
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user; // Return the matching user
            }
        }
        return null; // No matching user found
    }
    @PostMapping("/joinEvent")
    public String joinEvent(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("joinEvent", requestBody);
    }
    @PostMapping("/leaveEvent")
    public String leaveEvent(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("leaveEvent", requestBody);
    }

    @PostMapping("/joinWaitlist")
    public String joinWaitlist(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("joinWaitlist", requestBody);
    }
    @PostMapping("/leaveWaitlist")
    public String leaveWaitlist(@RequestBody Map<String, Object> requestBody) {
        // Send request to handler
        return userHandler.handleRequest("leaveWaitlist", requestBody);
    }

    

    @PostMapping("/search")
    public List<Event> getFilteredEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");
        List<Event> filteredEvents = new ArrayList<>();

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return filteredEvents;
        }

        // Read events data from the JSON file
        List<Event> events = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });

        String keyword = (String) searchRequest.get("keyword");
        List<String> filters = (List<String>) searchRequest.get("filters");

        // Filter events by keyword and filters
        for (Event event : events) {
            // Check if the keyword exists in the event location
            if ((event.getLocation().getLocation().contains(keyword) || event.getDate().contains(keyword)
                    || event.getTime().contains(keyword) || event.getOwner().getUsername().contains(keyword))
                    && filters.size() != 0) {
                // Check if all filters exist in the event's tags
                boolean allFiltersFound = true;
                for (String filter : filters) {
                    if (!event.getTags().contains(filter)) {
                        allFiltersFound = false;
                        break;
                    }
                }
                if (allFiltersFound) {
                    filteredEvents.add(event);
                }
            } else {
                if (event.getLocation().getLocation().contains(keyword) || event.getDate().contains(keyword)
                        || event.getTime().contains(keyword) || event.getOwner().getUsername().contains(keyword)) {
                    filteredEvents.add(event);
                }
            }
        }

        return filteredEvents;
    }

    @PostMapping("/generate_open")
    public List<Event> getOpenEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
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

    @Autowired
    private Database storage;

    /*
     * @GetMapping("/events")
     * public String events(Model model) {
     * List<Event> events = storage.listEvents();
     * System.out.println("Events retrieved from storage: " + events);
     * model.addAttribute("events", events);
     * return "events"; // This will return events.html template
     * }
     */
}
