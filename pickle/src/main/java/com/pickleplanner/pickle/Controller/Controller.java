package com.pickleplanner.pickle.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.EventHandler;
import com.pickleplanner.pickle.Persistence.Storage;
import com.pickleplanner.pickle.User.UserHandler;

@RestController
public class Controller {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private EventHandler eventHandler;

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

    @PostMapping("/acceptInvite/{token}")
    public String acceptInvite(@PathVariable String token, Model model) {
        // Use the token to identify the user and the event
        // Perform the action to join the user to the event

        // For example, you can render a page confirming the join
        return "join_confirmation";
    }

    @PostMapping("/declineInvite")
    public String declineInvite(Model model) {
        // You can add any necessary logic here, such as retrieving event details or
        // user information

        // For example, you can add a message to be displayed in the events.html page
        model.addAttribute("message", "Sorry, this event wasn't for you. Try some others.");

        // Redirect to events.html
        return "redirect:/events.html";
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
        return eventHandler.handleRequest("deleteEvent", requestBody);
    }

    @Autowired
    private Storage storage;

    @GetMapping("/events")
    public String events(Model model) {
        List<Event> events = storage.listEvents();
        System.out.println("Events retrieved from storage: " + events);
        model.addAttribute("events", events);
        return "events"; // This will return events.html template
    }
}
