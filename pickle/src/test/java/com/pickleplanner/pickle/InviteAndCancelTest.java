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
    public void testInviteUserWrongEmail() {
        // Simulate wrong email address
        String wrongEmail = "wrongemail";

        // Perform the invitation
        String result = userOperations.sendInvitation(wrongEmail);

        // Verify that the result indicates failure
        assertEquals("Failed to send invitation. Reason: Failed to send email.", result);
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
    public void testCancelInvitationWrongEmail() {
        // Simulate wrong email address
        String wrongEmail = "wrongemail";

        // Perform the cancellation
        String result = userOperations.cancelInvitation(wrongEmail);

        // Verify that the result indicates failure
        assertNotNull(result);
        assertEquals("Failed to send invitation cancellation message. Reason: Failed to send email.", result);
    }
}
