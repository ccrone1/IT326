package com.pickleplanner.pickle.Bracket;

import org.w3c.dom.events.Event;

public class Bracket {

    private int numOfPlayers;
    private String bracketId;
    private Event event;

    // Constructor
    public Bracket() {
        this.numOfPlayers = 0;
        int randomBracketId = (int) Math.floor(Math.random() * 100000);
        this.bracketId = String.valueOf(randomBracketId);
    }

    public Bracket(Event event) {

    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public String getBracketId() {
        return bracketId;
    }

    public Event getEvent() {
        return event;
    }

    // Method to edit the bracket
    public void editBracket() {

    }

    // Method to update the bracket
    public void updateBracket() {

    }
}
