package com.pickleplanner.pickle.Controller;

import java.util.Scanner;

import com.pickleplanner.pickle.Bracket.BracketHandler;
import com.pickleplanner.pickle.Event.EventHandler;
import com.pickleplanner.pickle.Location.LocationHandler;
import com.pickleplanner.pickle.Search_Filter.SearchHandler;
import com.pickleplanner.pickle.Tag.TagHandler;
import com.pickleplanner.pickle.User.UserHandler;

public class Controller {
    private UserHandler userHandler;
    private EventHandler eventHandler;
    private LocationHandler locationHandler;
    private SearchHandler searchHandler;
    private TagHandler tagHandler;
    private BracketHandler bracketHandler;

    public Controller() {
        // Initialize all handler classes
        userHandler = new UserHandler();
        eventHandler = new EventHandler();
        locationHandler = new LocationHandler();
        searchHandler = new SearchHandler();
        tagHandler = new TagHandler();
        bracketHandler = new BracketHandler();
    }

    // Method to start the application and handle user input
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            // Display menu options
            System.out.println("1. Create User");
            System.out.println("2. Create Event");
            // Add more menu options as needed

            // Get user input
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            // Process user input
            switch (option) {
                case 1:
                    createUser();
                    break;
                case 2:
                    createEvent();
                    break;
                // Add more cases to handle other menu options
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            // Ask user if they want to continue
            System.out.print("Do you want to continue? (yes/no): ");
            String continueOption = scanner.next();

            if (!continueOption.equalsIgnoreCase("yes")) {
                isRunning = false;
            }
        }

        scanner.close();
    }

    // Method to create a user
    private void createUser() {
        // Call method in UserHandler to create a user
        userHandler.createUser();
    }

    // Method to create an event
    private void createEvent() {
        // Call method in EventHandler to create an event
        eventHandler.createEvent();
    }

    // Add more methods to handle other user actions as needed
}
