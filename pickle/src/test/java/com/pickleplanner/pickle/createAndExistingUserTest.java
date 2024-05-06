package com.pickleplanner.pickle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pickleplanner.pickle.Location.Location;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.User;
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
        UserProfileManager manager = new UserProfileManager();
        // Existing user details
        String existingUsername = "existingUser";
        String existingEmail = "existing@example.com";
        String existingPassword = "existingPassword";
        
        // Attempt to register the existing user again
        boolean registrationResult = manager.registerNewUser(existingUsername, existingEmail, existingPassword);
        
        // show that registration failed due to existing user
        assertFalse("User registration should return false for existing user", registrationResult);
        
        
    }

    @Test
    public void testRegisterNewUser() throws Exception {
       
        UserProfileManager manager = new UserProfileManager();
        // User details
       String username = "testUser";
       String email = "test@example.com";
       String password = "password123";
       
       // Attempt to register the user
       boolean registrationResult = manager.registerNewUser(username, email, password);
       
       // show that registration was successful
       assertTrue("User registration should return true for success", registrationResult);
    }

}