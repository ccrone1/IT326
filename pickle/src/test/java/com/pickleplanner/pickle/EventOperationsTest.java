package com.pickleplanner.pickle;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.pickleplanner.pickle.Event.EventOperations;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.UserOperations;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventOperationsTest {

    @Autowired
    private UserOperations userOperations;

    @MockBean
    private Database storage; // Mock the storage dependency

    @Mock
    private JavaMailSender emailSender; // Mock the emailSender dependency

    @Test
    public void testInviteUser() {
        String result = userOperations.sendInvitation("test@example.com");
        assertEquals("Invitation sent successfully to test@example.com", result);
    }

    @Test
    public void testInviteUserNoEmail() {
        String result = userOperations.sendInvitation(null);
        assertEquals("Failed to send invitation. User email is missing or invalid.", result);
    }

    @Test
    public void testCancelInviteUser() {
        String result = userOperations.cancelInvitation("test@example.com");
        assertEquals("Invitation cancellation message sent successfully to test@example.com", result);
    }

    @Test
    public void testCancelInviteUserNoEmail() {
        String result = userOperations.cancelInvitation(null);
        assertEquals("Failed to cancel invitation. User email is missing or invalid.", result);
    }

    @Test
    public void testInviteUserWrongEmail() {
        // Simulate wrong email address
        String wrongEmail = "wrongemail";

        // Mocking the email sending to simulate a failure

        // Perform the invitation
        String result = eventOperations.sendInvitation(wrongEmail);

        // Verify that the result indicates failure
        assertEquals("Failed to send invitation. Reason: Failed to send email.", result);
    }

    @Test
    public void testCancelInvitationWrongEmail() {
        // Simulate wrong email address
        String wrongEmail = "wrongemail";

        // Mocking the email sending to simulate a failure
        Mockito.doThrow(new MailSendException("Failed to send email.")).when(emailSender).send(Mockito.any());

        // Perform the cancellation
        String result = eventOperations.cancelInvitation(wrongEmail);

        // Verify that the result indicates failure
        assertNotNull(result);
        assertEquals("Failed to send invitation cancellation message. Reason: Failed to send email.", result);
    }

    /*
     * @Test
     * public void testCancelInviteUser() {
     * // Test canceling an invitation
     * EventOperations eventOperations = new EventOperations();
     * String result = eventOperations.cancelInvitation("test@example.com");
     * assertEquals("Invitation canceled for test@example.com", result);
     * }
     * 
     * @Test
     * public void testAcceptInvite() {
     * // Test accepting an invitation
     * EventOperations eventOperations = new EventOperations();
     * String result = eventOperations.acceptInvitation("test@example.com");
     * assertEquals("Invitation accepted for test@example.com", result);
     * }
     * 
     * @Test
     * public void testDeclineInvite() {
     * // Test declining an invitation
     * EventOperations eventOperations = new EventOperations();
     * String result = eventOperations.declineInvitation("test@example.com");
     * assertEquals("Invitation declined for test@example.com", result);
     * }
     * 
     * @Test
     * public void testRetrieveEventDetails() {
     * // Test retrieving event details
     * EventOperations eventOperations = new EventOperations();
     * EventOperations eventDetails =
     * eventOperations.retrieveEventDetails("EventID123");
     * assertNotNull(eventDetails);
     * assertEquals("Pickleball Tournament", eventDetails.getTitle());
     * assertEquals("2024-06-15", eventDetails.getDate());
     * assertEquals("Manhattan, NY", eventDetails.getLocation());
     * }
     */
}
