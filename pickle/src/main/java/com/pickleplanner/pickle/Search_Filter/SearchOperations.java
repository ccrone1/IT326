package com.pickleplanner.pickle.Search_Filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickleplanner.pickle.Event.Event;

@Component
public class SearchOperations {

    public List<Event> filterEvents(@RequestBody Map<String, Object> searchRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("event_output.json");
        List<Event> filteredEvents = new ArrayList<>();

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return filteredEvents;
        }

        // Read events data from the JSON file
        List<Event> events = objectMapper.readValue(file, new TypeReference<List<Event>>() {
        });

        String keyword = (String) searchRequest.get("keyword");
        @SuppressWarnings("unchecked")
        List<String> filters = (List<String>) searchRequest.get("filters");

        // Filter events by keyword and filters
        for (Event event : events) {
            // Check if the keyword exists in the event location
            if ((event.getLocation().getLocation().contains(keyword) || event.getDate().contains(keyword)
                    || event.getTime().contains(keyword) || event.getOwner().getUsername().contains(keyword))
                    && filters.size() != 0) {
                // Check if all filters exist in the event's tags
                boolean allFiltersFound = true;
                for (String filter : filters) {
                    if (!event.getTags().contains(filter)) {
                        allFiltersFound = false;
                        break;
                    }
                }
                if (allFiltersFound) {
                    filteredEvents.add(event);
                }
            } else {
                if (event.getLocation().getLocation().contains(keyword) || event.getDate().contains(keyword)
                        || event.getTime().contains(keyword) || event.getOwner().getUsername().contains(keyword)) {
                    filteredEvents.add(event);
                }
            }
        }

        return filteredEvents;
    }

}
