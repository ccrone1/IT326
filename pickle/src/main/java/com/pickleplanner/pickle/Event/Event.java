package com.pickleplanner.pickle.Event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import com.pickleplanner.pickle.Bracket.Bracket;

public class Event {
    private String eventID;
    private String date;
    private String time;

    // Constructor
    public Event(String eventID, String date, String time) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
    }

    // Method to generate open events
    public ArrayList<Event> generateOpenEvents(Search search) {

        return new ArrayList<>();
    }

    // Method to get event details
    public EventDetails getEventDetails(String eventID) {

        return new EventDetails();
    }

    // Method to create a bracket
    public void createBracket(Bracket bracket) {

        this.bracket = bracket;
    }

    // Method to edit event
    public void editEvent(User owner, String eventID) {

    }
}
