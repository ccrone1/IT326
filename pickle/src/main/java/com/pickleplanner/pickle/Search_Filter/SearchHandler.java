package com.pickleplanner.pickle.Search_Filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.pickleplanner.pickle.Event.Event;

@Component
public class SearchHandler {

    @Autowired
    private SearchOperations searchOperations;

    public SearchHandler() {
        searchOperations = new SearchOperations();
    }

    public List<Event> handleRequest(@RequestBody Map<String, Object> searchRequest) throws IOException {
        return searchOperations.filterEvents(searchRequest);
    }
}