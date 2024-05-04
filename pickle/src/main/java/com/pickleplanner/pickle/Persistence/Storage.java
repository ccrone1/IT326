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
        Event event1 = new Event("Event 1", "4/2/22", "Location 1");
        Event event2 = new Event("Event 2", "3/3/3", "Location 2");
        Event event3 = new Event("Event 3", "marroon", "Location 3");

        events.add(event1);
        events.add(event2);
        events.add(event3);
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
        Event event5 = new Event("Event 1", "4/2/22", "Location 1");
        events.add(event5);
        return events;
    }

}
