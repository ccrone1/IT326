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
public class EditAndDeleteTest {

    @InjectMocks
    private UserOperations userOperations;

    @Mock
    private FileReader fileReader;

    @Mock
    private FileWriter fileWriter;

    @Mock
    private Database storage;

    @Test
    public void testCreateEventWithInvalidUsername() throws Exception {

        // Mock the requestData map
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("userName", "hmess1");
        requestData.put("id", "86604");
        requestData.put("date", "2024-05-08");
        requestData.put("time", "04:50");
        requestData.put("availability", 4);
        requestData.put("location", new Location("Ironwood Park"));
        requestData.put("tags", Arrays.asList("Tag1", "Tag2"));

        // Call the method under test
        String result = userOperations.createEvent(requestData);

        // Assert the result
        assertEquals("Username invalid.", result);
    }

    @Test
    public void testDelteEventWithInvalidEventID() throws Exception {
        // Mock the requestData map
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("eventId", "-1");

        // Call the method under test
        String result = userOperations.deleteEvent(requestData);

        // Assert the result
        assertEquals("EventID invalid.", result);
    }

}
