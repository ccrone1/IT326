package com.pickleplanner.pickle.User;

import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.Waitlist;
import com.pickleplanner.pickle.Location.Location;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class UserOperations {

    @Autowired
    private final JavaMailSender emailSender;

    public UserOperations(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // Send the invitation to a user's email
    public String sendInvitation(String userEmail, Integer eventID, String username) {
        // Check if user email is null
        if (userEmail == null || !isValidEmail(userEmail)) {
            return "Failed to send invitation. User email is missing or invalid.";
        }
        if (username == null) {
            return "Failed to send invitation. Username is missing or invalid.";
        }
        if (eventID == null) {
            return "Failed to send invitation. Event ID is missing or invalid.";
        }

        // Create a MimeMessage object
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setTo(userEmail);
            helper.setSubject("Invitation to Pickleball Event");

            // Include accept and decline links in the email body
            String acceptLink = "http://localhost:8080/accept?eventId=" + eventID;
            String declineLink = "http://localhost:8080/decline?eventId=" + eventID + "&action=decline";

            // Create email body with image
            String emailBody = "<html><body>"
                    + "<p>You have been invited to join a pickleball event by " + username + " with the Event ID "
                    + eventID + "!</p>"
                    + "<p><a style='display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; border-radius: 5px;' href='"
                    + acceptLink + "'>Accept</a>"
                    + "<a style='display: inline-block; background-color: #f44336; color: white; padding: 10px 20px; text-align: center; text-decoration: none; border-radius: 5px; margin-left: 10px;' href='"
                    + declineLink + "'>Decline</a></p>"
                    + "<p><img src='cid:image' /></p>"
                    + "<p>Address: 1471 Broadway, New York, NY</p>"
                    + "<p>Phone: +1-800-588-2300</p>"
                    + "<p>Website: <a href='http://localhost:8080'>Pickle Planner</a></p>"
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

    // Send a cancellation email to a user's email
    public String cancelInvitation(String userEmail, Integer eventID, String username) {
        // Check if user email is null
        if (userEmail == null || !isValidEmail(userEmail)) {
            return "Failed to cancel invitation. User email is missing or invalid.";
        }
        if (username == null) {
            return "Failed to cancel invitation. Username is missing or invalid.";
        }
        if (eventID == null) {
            return "Failed to cancel invitation. Event ID is missing or invalid.";
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
                    + "<p>Your invitation to the pickleball event from " + username + " with Event ID: " + eventID
                    + " has been canceled.</p>"
                    + "<p>We apologize for any inconvenience.</p>"
                    + "<p><img src='cid:image' /></p>"
                    + "<p>Address: 1471 Broadway, New York, NY</p>"
                    + "<p>Phone: +1-800-588-2300</p>"
                    + "<p>Website: <a href='http://localhost:8080'>Pickle Planner</a></p>"
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

    // Utilize regular expressions to see if the email is valid
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    // Code for creating an event
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
            @SuppressWarnings("unchecked")
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

    public User searchUser(String username) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("user_data.json");

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return null;
        }

        // Read user data from the JSON file
        User[] users = objectMapper.readValue(file, User[].class);

        // Find user by username
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user; // Return the matching user
            }
        }
        return null; // No matching user found
    }

    // Code for deleting an event
    public String deleteEvent(Map<String, Object> requestData) {
        String eventId = requestData.get("eventId").toString();
        List<Event> events = new ArrayList<Event>();
        try (FileReader reader = new FileReader("event_output.json")) {
            Type eventList = new TypeToken<List<Event>>() {
            }.getType();

            events = new Gson().fromJson(reader, eventList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Event targetEvent = events.stream()
                .filter(u -> u.getEventID().equals(eventId))
                .findFirst()
                .orElse(null);
        int eventIndex = events.indexOf(targetEvent);
        events.remove(eventIndex);

        Gson gson = new Gson();
        String json = gson.toJson(events);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("event_output.json", false)) {
            writer.write(json);
            return "New JSON data appended to event_output.json";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    // Code for adding a user to your following list
    public String addFollower(Map<String, Object> requestData) {
        String user_username = requestData.get("user_username").toString();
        String follower_username = requestData.get("follower_username").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(user_username))
                .findFirst()
                .orElse(null);

        User followerUser = users.stream()
                .filter(u -> u.getUsername().equals(follower_username))
                .findFirst()
                .orElse(null);

        targetUser.getFollowingList().add(followerUser);

        Gson gson = new Gson();
        String json = gson.toJson(users);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("user_data.json", false)) {
            writer.write(json);
            return "New JSON data appended to user_data.json";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    public String removeFollower(Map<String, Object> requestData) {
        String user_username = requestData.get("user_username").toString();
        String follower_username = requestData.get("follower_username").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(user_username))
                .findFirst()
                .orElse(null);

        for (int i = 0; i < targetUser.getFollowingList().size(); i++) {
            if (targetUser.getFollowingList().get(i).getUsername().equals(follower_username)) {
                targetUser.getFollowingList().remove(i);
                break;
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(users);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("user_data.json", false)) {
            writer.write(json);
            return "New JSON data appended to user_data.json";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    public String joinWaitlist(String event, String user) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event))
                .findFirst()
                .orElse(null);

        User targetUser = targetEvent.getWaitlist().getWaitList().stream()
                .filter(u -> u.getUsername().equals(user))
                .findFirst()
                .orElse(null);

        if (targetUser != null) {
            targetEvent.getWaitlist().getWaitList().add(targetUser);

        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
            return "Successfully added to waitlist";
        } catch (IOException e1) {

            e1.printStackTrace();
            return "Failed to add to waitlist";
        }

    }

    public String leaveWaitlist(String eventID, String username) {
        try {
            // Read JSON from file
            Type listType = new TypeToken<List<Event>>() {
            }.getType();
            List<Event> events;
            try (FileReader reader = new FileReader("event_output.json")) {
                events = new Gson().fromJson(reader, listType);
            }

            // Find the target event
            for (Event event : events) {
                if (event.getEventID().equals(eventID)) {
                    // Remove the user from the waitlist
                    event.getWaitlist().getWaitList().removeIf(user -> user.getUsername().equals(username));
                    // Write the updated JSON back to the file
                    try (FileWriter writer = new FileWriter("event_output.json")) {
                        new Gson().toJson(events, writer);
                        return "Successfully left waitlist";
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Failed leaving waitlist: Unable to write to file";
                    }
                }
            }
            return "Failed leaving waitlist: Event not found";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Failed leaving waitlist: File not found";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed leaving waitlist: Error reading file";
        }
    }

    public String joinEvent(Map<String, Object> requestData) {
        String user_username = requestData.get("username").toString();
        String eventId = requestData.get("eventId").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type eventList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, eventList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(user_username))
                .findFirst()
                .orElse(null);

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
        Event targetEvent = existingEvents.stream()
                .filter(u -> u.getEventID().equals(eventId))
                .findFirst()
                .orElse(null);

        targetEvent.getParticipants().add(targetUser);

        Gson gson = new Gson();
        String json = gson.toJson(users);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("event_output.json", false)) {
            writer.write(json);
            return "New JSON data appended to json";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    /*
     * public String joinEvent(String userName, String event) throws IOException {
     * 
     * // Reading existing events from the JSON file and storing them into an
     * arraylist
     * List<Event> existingEvents = new ArrayList<Event>();
     * try (FileReader reader = new FileReader("event_output.json")) {
     * Type listType2 = new TypeToken<List<Event>>() {
     * }.getType();
     * 
     * existingEvents = new Gson().fromJson(reader, listType2);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * // If the the JSON file is empty the array will become null so we are
     * // re-intializing it so we can add an event to it
     * if (existingEvents == null)
     * existingEvents = new ArrayList<Event>();
     * 
     * // Append new Event objects to existing list
     * Event targetEvent = existingEvents.stream()
     * .filter(u -> u.getEventID().equals(event))
     * .findFirst()
     * .orElse(null);
     * 
     * if (targetEvent != null) {
     * User userToAdd = searchUser(userName);
     * 
     * targetEvent.getParticipants().add(userToAdd);
     * targetEvent.setAvailability(targetEvent.getAvailability() - 1);
     * 
     * }
     * // }
     * Gson gson = new Gson();
     * String json = gson.toJson(existingEvents);
     * 
     * // Write JSON to file (append mode)
     * try (FileWriter writer = new FileWriter("event_output.json", false)) {
     * writer.write(json);
     * return "New JSON data appended to event_output.json";
     * } catch (IOException e) {
     * e.printStackTrace();
     * return "Failed to create JSON";
     * }
     * 
     * }
     */
    public String LeaveEvent(String user, String event) {

        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getParticipants().stream()
                    .filter(u -> u.getUsername().equals(user))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {

                targetEvent.getParticipants().remove(userToRemove);
                targetEvent.setAvailability(targetEvent.getAvailability() + 1);

            }
        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
            return "Successfullyleft Event";
        } catch (IOException e1) {

            e1.printStackTrace();
            return "Failed to leave event";
        }

    }

    public String kickFromEvent(String user, String event) {
        Type listType = new TypeToken<List<Event>>() {
        }.getType();
        List<Event> events = new Gson().fromJson("event_output.json", listType);

        Event targetEvent = events.stream()
                .filter(e -> e.getEventID().equals(event))
                .findFirst()
                .orElse(null);

        if (targetEvent != null) {
            User userToRemove = targetEvent.getParticipants().stream()
                    .filter(u -> u.getUsername().equals(user))
                    .findFirst()
                    .orElse(null);

            if (userToRemove != null) {

                targetEvent.getParticipants().remove(userToRemove);
                targetEvent.setAvailability(targetEvent.getAvailability() + 1);

            }
        }
        String updatedJson = new Gson().toJson(events);
        FileWriter writer;
        try {
            writer = new FileWriter("event_output.json");
            writer.write(updatedJson);
            writer.close();
            return "Successfully Kicked from Event";
        } catch (IOException e1) {

            e1.printStackTrace();
            return "Failed to kick from Event";
        }

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
    public String deleteProfile(Map<String, Object> requestData) {
        String userName = requestData.get("username").toString();
        List<User> users = new ArrayList<User>();
        try (FileReader reader = new FileReader("user_data.json")) {
            Type userList = new TypeToken<List<User>>() {
            }.getType();

            users = new Gson().fromJson(reader, userList);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed to read JSON file";
        }

        User targetUser = users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findFirst()
                .orElse(null);

        if (targetUser != null) {
            users.remove(targetUser);
        } else {
            return "Profile " + userName + " not found.";
        }

        Gson gson = new Gson();
        String json = gson.toJson(users);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("user_data.json", false)) {
            writer.write(json);
            return "profile " + userName + " deleted successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete profile";
        }
    }

    public String editProfile(Map<String, Object> requestData) {
        String fname = requestData.get("firstName").toString();
        String lname = requestData.get("lastName").toString();
        String userName = requestData.get("userName").toString();
        String skillLevel = requestData.get("skillLevel").toString();
        String email = requestData.get("email").toString();
        String password = requestData.get("password").toString();

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

        targetUser.setLastName(lname);
        targetUser.setFirstName(fname);
        targetUser.setEmail(email);
        targetUser.setSkillLevel(skillLevel);
        targetUser.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(users);

        // Write JSON to file (append mode)
        try (FileWriter writer = new FileWriter("user_data.json", false)) {
            writer.write(json);
            return "New JSON data appended to user_data.json";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    public class login {
        public boolean verifyUser(String username, String password) {
            // get user data
            List<User> users = new ArrayList<>();
            try (FileReader reader = new FileReader("user_data.json")) {
                Type userList = new TypeToken<List<User>>() {
                }.getType();

                users = new Gson().fromJson(reader, userList);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
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
