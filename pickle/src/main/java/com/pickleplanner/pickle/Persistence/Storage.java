package com.pickleplanner.pickle.Persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.Event.Event;

@Component
public class Storage {

    private List<Event> events;

    // Constructor
    // Constructor
    public Storage() {
        this.events = new ArrayList<>();

        // Add sample events for testing
    }

    // Method to add event to the storage
    public void save(Event event) {
        events.add(event);
    }

    // Method to remove event from the storage
    public Event retrieve(Event event) {
        for (Event storedEvent : events) {
            if (storedEvent.equals(event)) {
                return storedEvent;
            }
        }
        return null; // Event not found
    }

    public List<Event> listEvents() {
        return events;
    }

}
