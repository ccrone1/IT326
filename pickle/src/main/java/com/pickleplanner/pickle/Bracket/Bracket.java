package com.pickleplanner.pickle.Bracket;

import java.util.Collections;
import java.util.List;

import com.pickleplanner.pickle.Event.Event;

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

    public String setParticipants(List<String> participantUsernames, Event event) {
        this.numOfPlayers = participantUsernames.size();
        int randomBracketId = (int) Math.floor(Math.random() * 100000);
        this.bracketId = String.valueOf(randomBracketId);
        this.event = event;

        // Shuffle the list of participant usernames to randomize the pairings
        Collections.shuffle(participantUsernames);

        // Initialize a StringBuilder to construct the matchups string
        StringBuilder matchupsBuilder = new StringBuilder();

        // Generate matches for each pair of participants
        if (numOfPlayers < 2) {
            // Handle case where there is only one participant
            matchupsBuilder.append("Only one participant: ").append(participantUsernames.get(0));
        } else {
            for (int i = 0; i < numOfPlayers - 1; i += 2) {
                String player1 = participantUsernames.get(i);
                String player2 = participantUsernames.get(i + 1);

                // Append the matchup to the StringBuilder
                matchupsBuilder.append(player1).append(" vs ").append(player2).append("\n");
            }
        }

        // If there's an odd number of participants, the last participant will not have
        // an opponent
        if (numOfPlayers % 2 != 0) {
            String lastParticipant = participantUsernames.get(numOfPlayers - 1);
            matchupsBuilder.append("\nUnmatched participant: ").append(lastParticipant);
        }

        return matchupsBuilder.toString();
    }

}
