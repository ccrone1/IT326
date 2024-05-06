package com.pickleplanner.pickle.Tag;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Tag {

    private ArrayList<String> tags;

    // Constructor
    public Tag() {
        this.tags = null;
    }

    public Tag(ArrayList<String> tags) {
        this.tags = tags;
    }

    // Method to add tags to an Event
    public ArrayList<String> getTags() {
        return tags;
    }
}