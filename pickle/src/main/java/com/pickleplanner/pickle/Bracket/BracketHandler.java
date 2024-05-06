package com.pickleplanner.pickle.Bracket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.pickleplanner.pickle.Event.Event;

@Component
public class BracketHandler {
    private BracketOperations bracketOperations;

    public BracketHandler() {
        bracketOperations = new BracketOperations();
    }

    public BracketOperations getBracketOperations() {
        return bracketOperations;
    }

    public List<Event> handleRequest(@RequestBody Map<String, String> searchRequest) throws IOException {
        return BracketOperations.createBracket(searchRequest);
    }
}