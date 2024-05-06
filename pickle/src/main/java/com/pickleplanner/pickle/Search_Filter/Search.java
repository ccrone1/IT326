package com.pickleplanner.pickle.Search_Filter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Search {
    private ArrayList<String> criteria;

    // Constructor
    public Search(ArrayList<String> criteria) {
        this.criteria = criteria;
    }

    public ArrayList<String> getCriteria() {
        return criteria;
    }
}