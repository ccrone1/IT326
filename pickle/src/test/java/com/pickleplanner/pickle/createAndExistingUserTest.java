package com.pickleplanner.pickle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.UserOperations;

@RunWith(SpringRunner.class)
@SpringBootTest
public class createAndExistingUserTest {

    @InjectMocks
    private UserOperations userOperations;

    @Mock
    private FileReader fileReader;

    @Mock
    private FileWriter fileWriter;

    @Mock
    private Database storage;

    @Test
    public void testRegisterExistingUser() {
        // Existing user details
        String existingUsername = "existingUser";
        String existingEmail = "existing@example.com";
        String existingPassword = "existingPassword";
        
        // Create a user first to simulate existing user
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", "Existing");
        userData.put("lastName", "User");
        userData.put("userName", existingUsername);
        userData.put("email", existingEmail);
        userData.put("password", existingPassword);
        userData.put("skillLevel", "Beginner");
        
        userOperations.createProfile(userData);
        
        // Attempt to register the existing user again
        String registrationResult = userOperations.createProfile(userData);
        
        // show that registration failed due to existing user
        assertEquals("Username already exists", registrationResult);
        
        
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        // User details
        String username = "testUser";
        String email = "test@example.com";
        String password = "password123";
        
        // Create user data map
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", "Test");
        userData.put("lastName", "User");
        userData.put("userName", username);
        userData.put("email", email);
        userData.put("password", password);
        userData.put("skillLevel", "Intermediate");
        
        // Attempt to register the user
        String registrationResult = userOperations.createProfile(userData);
        
        // show that registration was successful
        assertEquals("New JSON data appended to user_data.json", registrationResult);
    }

}