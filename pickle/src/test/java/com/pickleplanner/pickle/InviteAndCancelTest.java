package com.pickleplanner.pickle;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.UserOperations;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InviteAndCancelTest {

    @Autowired
    private UserOperations userOperations;

    @MockBean
    private Database storage;

    @Mock
    private JavaMailSender emailSender;

    @Test
    public void testInviteUser() {
        String result = userOperations.sendInvitation("test@example.com", 15, "aathom7");
        assertEquals("Invitation sent successfully to test@example.com", result);
    }

    @Test
    public void testInviteUserNoEmail() {
        String result = userOperations.sendInvitation(null, 15, "aathom7");
        assertEquals("Failed to send invitation. User email is missing or invalid.", result);
    }

    @Test
    public void testInviteUserNoUsername() {
        String result = userOperations.sendInvitation("test@example.com", 15, null);
        assertEquals("Failed to send invitation. Username is missing or invalid.", result);
    }

    @Test
    public void testInviteUserNoEventID() {
        String result = userOperations.sendInvitation("test@example.com", null, "aathom7");
        assertEquals("Failed to send invitation. Event ID is missing or invalid.", result);
    }

    @Test
    public void testInviteUserWrongEmail() {
        String result = userOperations.sendInvitation("wrongemail", 15, "aathom7");
        assertEquals("Failed to send invitation. User email is missing or invalid.", result);
    }

    @Test
    public void testCancelInviteUser() {
        String result = userOperations.cancelInvitation("test@example.com", 15, "aathom7");
        assertEquals("Invitation cancellation message sent successfully to test@example.com", result);
    }

    @Test
    public void testCancelInviteUserNoEmail() {
        String result = userOperations.cancelInvitation(null, 15, "aathom7");
        assertEquals("Failed to cancel invitation. User email is missing or invalid.", result);
    }

    @Test
    public void testCancelInviteUserNoEventID() {
        String result = userOperations.cancelInvitation("alexthomas7250@gmail.com", null, "aathom7");
        assertEquals("Failed to cancel invitation. Event ID is missing or invalid.", result);
    }

    @Test
    public void testCancelInviteUserNoUsername() {
        String result = userOperations.cancelInvitation("alexthomas7250@gmail.com", 15, null);
        assertEquals("Failed to cancel invitation. Username is missing or invalid.", result);
    }

    @Test
    public void testCancelInvitationWrongEmail() {
        String result = userOperations.cancelInvitation("wrongEmail", 15, "aathom7");

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
