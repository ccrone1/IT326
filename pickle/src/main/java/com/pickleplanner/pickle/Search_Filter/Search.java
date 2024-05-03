package com.pickleplanner.pickle.Search_Filter;

import java.util.ArrayList;

import com.pickleplanner.pickle.Event.Event;

public class Search {

    // Properties
    private ArrayList<String> criteria;
    private Event event;

    // Constructor
    public Search() {
        // Initialize properties
    }

    // Methods

    /**
     * Setter for criteria.
     * 
     * @param criteria The criteria to set.
     */
    public void setCriteria(ArrayList<String> criteria) {
        this.criteria = criteria;
    }

    /**
     * Getter for criteria.
     * 
     * @return The criteria.
     */
    public ArrayList<String> getCriteria() {
        return criteria;
    }

    /**
     * Setter for event.
     * 
     * @param event The event to set.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Getter for event.
     * 
     * @return The event.
     */
    public Event getEvent() {
        return event;
    }
}
