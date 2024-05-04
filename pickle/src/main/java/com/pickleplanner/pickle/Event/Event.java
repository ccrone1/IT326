package com.pickleplanner.pickle.Event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Location.Location;
import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.Search_Filter.Search;
import com.pickleplanner.pickle.Tag.Tag;

public class Event {
    private String eventID;
    private String date;
    private String time;
    private int availability;
    private User owner;
    private Location location;
    // List<Tag>
    private List<String> tags;
    private Bracket bracket;
    private List<User> participants;
    private Waitlist waitlist;
    private Invitation invitation;
    private Search search;

    // Constructor
    public Event(String eventID, String date, String time, int availability, User owner, Location location,
            List<String> tags, Bracket bracket, List<User> participants, Waitlist waitlist) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.availability = availability;
        this.owner = owner;
        this.location = location;
        this.tags = tags;
        this.bracket = bracket;
        this.participants = participants;
        this.waitlist = waitlist;
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

    public List<User> getParticipants() {
        return this.participants;
    }

    // Method to edit event
    public void editEvent(User owner, String eventID) {

    }
}
