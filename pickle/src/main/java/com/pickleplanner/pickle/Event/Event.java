package com.pickleplanner.pickle.Event;

import java.util.ArrayList;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Location.Location;
import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.Search_Filter.Search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public Event() {
    }

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

    public String getEventID() {
        return this.eventID;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public int getAvailability() {
        return this.availability;
    }

    public void setAvailability(int num) {
        this.availability = num;
    }

    public User getOwner() {
        return this.owner;
    }

    public Location getLocation() {
        return this.location;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public Bracket getBracket() {
        return this.bracket;
    }

    public Invitation getInvitation() {
        return this.invitation;
    }

    public Search getSearch() {
        return this.search;
    }

    public Waitlist getWaitlist() {
        return this.waitlist;
    }

    // Method to generate open events
    public ArrayList<Event> generateOpenEvents(Search search) {

        return new ArrayList<>();
    }

    // Method to get event details
    public Event getEventDetails(String eventID) throws IOException {
        // Load events from JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        List<Event> events = objectMapper.readValue(new File("events.json"), new TypeReference<List<Event>>() {
        });

        // Find the event with the given ID
        for (Event event : events) {
            if (event.getEventID().equals(eventID)) {
                return event;
            }
        }

        // If event with the given ID is not found, return null or throw an exception
        return null;
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
