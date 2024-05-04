package com.pickleplanner.pickle.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        return eventHandler.handleRequest("inviteUser", requestBody);
    }

    @PostMapping("/cancelInvite")
    public String cancelInvite(@RequestBody Map<String, Object> requestBody) {
        // Send the request to the handler
        return eventHandler.handleRequest("cancelInvite", requestBody);
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
