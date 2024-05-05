package com.pickleplanner.pickle.User;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.Waitlist;
import com.pickleplanner.pickle.Location.Location;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.Tag.Tag;
import com.pickleplanner.pickle.User.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class UserOperations {

    private final JavaMailSender emailSender;

    @Autowired
    public UserOperations(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private Database storage;

    public String sendInvitation(String userEmail) {
        // Check if user email is null
        if (userEmail == null || !isValidEmail(userEmail)) {
            return "Failed to send invitation. User email is missing or invalid.";
        }

        // Create a SimpleMailMessage object
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setTo(userEmail);
            helper.setSubject("Invitation to Pickleball Event");

            // Include accept and decline links in the email body
            String acceptLink = "http://localhost:8080/accept";
            String declineLink = "http://localhost:8080/decline";

            // Create email body with image
            String emailBody = "<html><body>"
                    + "<p>You have been invited to join a pickleball event!</p>"
                    + "<p><a style='display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; border-radius: 5px;' href='"
                    + acceptLink + "'>Accept</a>"
                    + "<a style='display: inline-block; background-color: #f44336; color: white; padding: 10px 20px; text-align: center; text-decoration: none; border-radius: 5px; margin-left: 10px;' href='"
                    + declineLink + "'>Decline</a></p>"
                    + "<p><img src='cid:image' /></p>"
                    + "<p>Address: 123 Main Street, Manhattan, NY</p>"
                    + "<p>Phone: 555-555-5555</p>"
                    + "<p>Website: <a href='http://www.pickleplanner.com'>www.pickleplanner.com</a></p>"
                    + "</body></html>";

            // Set email body
            helper.setText(emailBody, true);

            // Set the image attachment
            ClassPathResource image = new ClassPathResource("IMG_0444.png");

            helper.addInline("image", image);

            // Send the email
            emailSender.send(message);

            return "Invitation sent successfully to " + userEmail;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send invitation cancellation message. Reason: " + e.getMessage();
        }
    }

    public String cancelInvitation(String userEmail) {
        // Check if user email is null
        if (userEmail == null || !isValidEmail(userEmail)) {
            return "Failed to cancel invitation. User email is missing or invalid.";
        }

        // Create a MimeMessage object
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setTo(userEmail);
            helper.setSubject("Cancellation of Pickleball Event Invitation");

            // Create email body
            String emailBody = "<html><body>"
                    + "<p>Your invitation to the pickleball event has been canceled.</p>"
                    + "<p>We apologize for any inconvenience.</p>"
                    + "<p><img src='cid:image' /></p>"
                    + "<p>Address: 123 Main Street, Manhattan, NY</p>"
                    + "<p>Phone: 555-555-5555</p>"
                    + "<p>Website: <a href='http://www.pickleplanner.com'>www.pickleplanner.com</a></p>"
                    + "</body></html>";
            ;

            // Set email body
            helper.setText(emailBody, true);

            ClassPathResource image = new ClassPathResource("IMG_0444.png");

            helper.addInline("image", image);

            // Send the email
            emailSender.send(message);

            return "Invitation cancellation message sent successfully to " + userEmail;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to cancel invitation cancellation message. Reason: " + e.getMessage();
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    public String createEvent(Map<String, Object> requestData) {
        String userName = requestData.get("userName").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findFirst()
                .orElse(null);
        // Making sure the username is valid
        if (targetUser != null) {

            Waitlist waitlist = new Waitlist();
            List<User> participants = new ArrayList<User>();
            Location loc = new Location(requestData.get("location").toString());
            List<String> tags = (List<String>) requestData.get("tags");
            Bracket bracket = new Bracket();
            participants.add(targetUser);
            Event event = new Event(
                    requestData.get("id").toString(),
                    requestData.get("date").toString(),
                    requestData.get("time").toString(),
                    Integer.parseInt(requestData.get("availability").toString()),
                    targetUser,
                    loc,
                    tags, bracket, participants, waitlist);

            // Reading existing events from the JSON file and storing them into an arraylist
            List<Event> existingEvents = new ArrayList<Event>();
            try (FileReader reader = new FileReader("event_output.json")) {
                Type listType2 = new TypeToken<List<Event>>() {
                }.getType();

                existingEvents = new Gson().fromJson(reader, listType2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // If the the JSON file is empty the array will become null so we are
            // re-intializing it so we can add an event to it
            if (existingEvents == null)
                existingEvents = new ArrayList<Event>();

            // Append new Event objects to existing list
            existingEvents.add(event);

            Gson gson = new Gson();
            String json = gson.toJson(existingEvents);

            // Write JSON to file (append mode)
            try (FileWriter writer = new FileWriter("event_output.json", false)) {
                writer.write(json);
                return "New JSON data appended to event_output.json";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to create JSON";
            }
        }
        return "Username invalid.";
    }

    // // Store in storage
    // Gson gson = new Gson();
    // // Convert object to JSON string
    // String json = gson.toJson(event);

    // // Write JSON string to a file
    // try (FileWriter writer = new FileWriter("event_output.json", true)) {
    // writer.write(json);
    // return "Successfully created JSON";
    // } catch (IOException e) {
    // e.printStackTrace();
    // return "Failed to create JSON";
    // }
    // }

    /*
     * public String deleteEvent(Map<String, Object> requestData) {
     * // When they click the button it sends the EventID
     * // Or when we display the event the ID is displayed and theyneed to type in
     * the
     * // ID to delete it
     * }
     */

    public void joinWaitlist(Event event, User user) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {

            targetEvent.getWaitlist().getWaitList().add(user);

        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

    public void leaveWaitlist(Event event, User user) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getWaitlist().getWaitList().stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {
                targetEvent.getWaitlist().getWaitList().remove(user);

            }
        }

        // Convert back to JSON if needed
        String updatedJson = new Gson().toJson(events);
        // need to push
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        // list.getWaitList().remove(user);

    }

    public void joinEvent(User user, Event event) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {

            targetEvent.getParticipants().add(user);
            targetEvent.setAvailability(targetEvent.getAvailability() - 1);

        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

    public void LeaveEvent(User user, Event event) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getParticipants().stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {
                targetEvent.getParticipants().remove(userToRemove);
                targetEvent.setAvailability(targetEvent.getAvailability()+1);

            }
        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        event.getParticipants().remove(user);
    }

    public void kickFromEvent(User user, Event event) {
        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        // Assuming you know which event and user to remove
        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event.getEventID()))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getParticipants().stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {
                targetEvent.getParticipants().remove(user);

            }
        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        event.getParticipants().remove(user);

    }

    // Create Profile Operations
    public String createProfile(Map<String, Object> requestData) {
        String userName = requestData.get("userName").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findFirst()
                .orElse(null);

        if (targetUser == null) {
            User user = new User(requestData.get("firstName").toString(), requestData.get("lastName").toString(),
                    requestData.get("userName").toString(), requestData.get("email").toString(),
                    requestData.get("password").toString(), requestData.get("skillLevel").toString());

            // Reading existing events from the JSON file and storing them into an arraylist
            List<User> existingUsers = new ArrayList<User>();
            try (FileReader reader = new FileReader("user_data.json")) {
                Type listType = new TypeToken<List<User>>() {
                }.getType();

                existingUsers = new Gson().fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // If the the JSON file is empty the array will become null so we are
            // re-intializing it so we can add an user to it
            if (existingUsers == null)
                existingUsers = new ArrayList<User>();

            // Append new user objects to existing list
            existingUsers.add(user);

            Gson gson = new Gson();
            String json = gson.toJson(existingUsers);

            // Write JSON to file (append mode)
            try (FileWriter writer = new FileWriter("user_data.json", false)) {
                writer.write(json);
                return "New JSON data appended to user_data.json";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to create JSON";
            }
        }
        return "Username already exists";

    }

    // Delete Profile
    public String deleteProfile(String userName) {
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete profile";
        }

        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findFirst()
                .orElse(null);

        if (targetUser != null) {
            users.remove(targetUser);

            Gson gson = new Gson();
            String json = gson.toJson(users);

            try (FileWriter writer = new FileWriter("user_data.json", false)) {
                writer.write(json);
                return "User profile deleted successfully";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to delete profile";
            }
        } else {
            return "User profile not found";
        }
    }

    // public String deleteProfile(String username) {
    // need to find the user based on the username?
    // assume you have a method in Storage class to retrieve the user by username
    /*
     * User userToDelete = storage.getUserByUsername(username);
     * 
     * if (userToDelete == null) {
     * return "User with username " + username + " not found.";
     * }
     * 
     * // Delete the user from database system
     * storage.deleteUser(userToDelete);
     * 
     * return "Profile deleted successfully for user: " + username;
     */

    // }

    public String editProfile(String username) {
        // need to find the user based on the username

        // gives me List of users
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = new Gson().fromJson("user_data.json", listType);

        User userToEdit = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userToEdit != null) {
            // Update user data
            userToEdit.firstName((String) updatedData.get("firstName"));
            userToEdit.setLastName((String) updatedData.get("lastName"));
            userToEdit.setEmail((String) updatedData.get("email"));
            userToEdit.setPassword((String) updatedData.get("password"));
            userToEdit.setSkillLevel((String) updatedData.get("skillLevel"));
        }

        String updatedJson = new Gson().toJson(users);
        FileWriter writer;
        try {
            writer = new FileWriter("user_data.json");
            writer.write(updatedJson);
            writer.close();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

    }

    public class login {
        boolean verifyUser(String username, String password) {
            // get user data
            List<User> users = new ArrayList<User>();
            try (FileReader reader = new FileReader("user_data.json")) {
                Type userList = new TypeToken<List<User>>() {
                }.getType();

                users = new Gson().fromJson(reader, userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            User targetUser = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            // check user exist and passW match
            return targetUser != null && targetUser.getPassword().equals(password);
        }

    }

    public class logout {

    }

}
