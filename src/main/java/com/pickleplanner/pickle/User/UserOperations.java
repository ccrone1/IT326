package com.pickleplanner.pickle.User;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.Bracket.Bracket;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.Event.Waitlist;
import com.pickleplanner.pickle.Location.Location;
import com.pickleplanner.pickle.Persistence.Storage;
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

    private Storage storage;

    public String sendInvitation(String userEmail) {
        // Check if user email is null
        if (userEmail == null) {
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
        if (userEmail == null) {
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
                    + "</body></html>";

            // Set email body
            helper.setText(emailBody, true);

            // Send the email
            emailSender.send(message);

            return "Invitation cancellation message sent successfully to " + userEmail;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send invitation cancellation message. Reason: " + e.getMessage();
        }
    }

    public String createEvent(Map<String, Object> requestData) {
        User user = new User("hogan17", "hog@hogmail.com", "suhail53");
        Waitlist waitlist = new Waitlist();
        List<User> participants = new ArrayList<User>();
        Location loc = new Location(requestData.get("location").toString());
        List<String> tags = (List<String>) requestData.get("tags");
        Bracket bracket = new Bracket();
        participants.add(user);
        Event event = new Event(
                requestData.get("id").toString(),
                requestData.get("date").toString(),
                requestData.get("time").toString(),
                Integer.parseInt(requestData.get("availability").toString()),
                user,
                loc,
                tags, bracket, participants, waitlist);
        // Store in storage
        Gson gson = new Gson();
        // Convert object to JSON string
        String json = gson.toJson(event);

        // Write JSON string to a file
        try (FileWriter writer = new FileWriter("event_output.json", true)) {
            writer.write(json);
            return "Successfully created JSON";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to create JSON";
        }
    }

    public void joinWaitlist(Waitlist list, User user) {
        list.getWaitList().add(user);
    }

    public void leaveWaitlist(Waitlist list, User user) {
        list.getWaitList().remove(user);

    }

    public void joinEvent(User user, Event event) {
        event.getParticipants().add(user);
    }

    public void LeaveEvent(User user, Event event) {
        event.getParticipants().remove(user);
    }

    public void kickFromEvent(User user, Event event) {
        event.getParticipants().remove(user);
    }
}
