package com.pickleplanner.pickle.Bracket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.User.User;

public class BracketOperations {
    public String createBracket(@RequestBody Map<String, String> requestBody) throws IOException {
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