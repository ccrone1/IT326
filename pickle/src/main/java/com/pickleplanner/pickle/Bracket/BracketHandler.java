package com.pickleplanner.pickle.Bracket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.EventOperations;

@Component
public class BracketHandler {

    private final BracketOperations bracketOperations;

    public BracketHandler() {
        bracketOperations = new BracketOperations();
    }

    public BracketOperations getBracketOperations() {
        return bracketOperations;
    }

    public String handleRequest(@RequestBody Map<String, String> requestBody) throws IOException {
        return bracketOperations.createBracket(requestBody);
    }
}