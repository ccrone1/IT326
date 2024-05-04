package com.pickleplanner.pickle.Event;

import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.Persistence.Storage;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.ClassPathResource;

@Component
public class EventOperations {

    @Autowired
    private JavaMailSender emailSender;

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

}
