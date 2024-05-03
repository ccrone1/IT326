package com.pickleplanner.pickle.Event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import com.pickleplanner.pickle.Bracket.Bracket;



import com.pickleplanner.pickle.User.User;//imports ueer
import com.pickleplanner.pickle.Tag.Tag;//import Tag
import com.pickleplanner.pickle.Event.Waitlist;//import waitlist
import com.pickleplanner.pickle.Location.Location;//import location
import com.pickleplanner.pickle.Search_Filter.Search;//import Search

public class Event {
    private String eventID;
    private Date date;
    private Time time;
    private int availability;
    private User owner;
    private Location location;
    private ArrayList<Tag> tags;
    private Bracket bracket;
    private ArrayList<User> participants;
    private Waitlist waitlist;

    // Constructor
    public Event(String eventID, Date date, Time time, int initialAvailability, User owner, Location location) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.availability = initialAvailability;
        this.owner = owner;
        this.location = location;
        this.tags = new ArrayList<Tag>();
        this.bracket = null;
        this.participants = new ArrayList<User>();
        this.waitlist = new Waitlist();
    }

    // Method to generate open events
    public ArrayList<Event> generateOpenEvents(Search search) {

        return new ArrayList<>();
    }

    // Method to get event details
    public String getEventDetails(String eventID) {

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