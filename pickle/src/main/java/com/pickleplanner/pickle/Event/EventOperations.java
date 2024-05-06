
package com.pickleplanner.pickle.Event;

import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.Waitlist;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

@Component
public class EventOperations {

    public void addToWaitlist(Event event, User user) {
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
                    .filter(u -> u.getUsername().equals(user.getUsername()))
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

    public void removeFromWaitlist(Event event, User user) {
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
                    .filter(u -> u.getUsername().equals(user.getUsername()))
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

    public String editEvent(Map<String, Object> requestData) {
        return "";
    }

}
