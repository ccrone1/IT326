package com.pickleplanner.pickle.Event;

import java.util.ArrayList;
import java.util.List;

import User;

public class Waitlist {
    private static List<User> waitlist = new ArrayList<>();

    public static void addToWaitlist(User user, Event event) {
        waitlist.add(user);
        System.out.println(user.getUsername() + " added to waitlist for event: " + event.getName());
    }

    public static void removeFromWaitlist(User user, Event event) {
        waitlist.remove(user);
        System.out.println(user.getUsername() + " removed from waitlist of event: " + event.getName());
    }
}