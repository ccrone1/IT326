package com.pickleplanner.pickle.Bracket;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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