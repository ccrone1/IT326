package com.pickleplanner.pickle.Search_Filter;

import java.util.ArrayList;

public class Search {
    private ArrayList<String> criteria;

    // Constructor
    public Search(ArrayList<String> criteria) {
        this.criteria = criteria;
    }

    // Method to filter details
    public void filterDetails(String eventId, ArrayList<String> criteria, Filter filter) {
        // Call the filter method in Filter class
        filter.filterEvents(eventId, criteria);
    }
}