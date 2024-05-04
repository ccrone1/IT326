package com.pickleplanner.pickle.Search_Filter;

import java.util.ArrayList;

import com.pickleplanner.pickle.Event.Event;

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

    // Method to filter events
    public void filterEvents(ArrayList<String> criteria) {

        updateFilteredEvents();
    }

    // Method to update filtered events
    private void updateFilteredEvents() {

    }
}
