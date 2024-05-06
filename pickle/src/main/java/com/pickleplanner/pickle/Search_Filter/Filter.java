package com.pickleplanner.pickle.Search_Filter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.Event.Event;

@Component
public class Filter {
    private ArrayList<Event> events;

    // Constructor
    public Filter(ArrayList<Event> events) {
        this.events = events;
    }

    // Getter method for events
    public ArrayList<Event> getEvents() {

        return events;
    }
}
