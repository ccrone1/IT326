package com.pickleplanner.pickle.Event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.User.User;


@Component
public class Waitlist {
    @Autowired
    private Event event;


    private static List<User> waitlist = new ArrayList<>();

    public static void addToWaitlist(User user, Event event) {
        waitlist.add(user);
        System.out.println(user.getUsername() + " added to waitlist for event: " + event.getEventDetails(event.eventID));
    }

    public static void removeFromWaitlist(User user, Event event) {
        waitlist.remove(user);
        System.out.println(user.getUsername() + " removed from waitlist of event: " + event.getEventDetails(event.eventID));
    }
}