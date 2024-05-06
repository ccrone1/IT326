package com.pickleplanner.pickle;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import com.pickleplanner.pickle.Persistence.Database;
import com.pickleplanner.pickle.User.UserOperations;
import com.pickleplanner.pickle.Event.Event;
import com.pickleplanner.pickle.User.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoinAndLeaveWaitlistTest {

    @Autowired
    private UserOperations userOperations;
    private Event event;
    private User user;
    @MockBean
    private Database storage; // Mock the storage dependency

    @Test
    public void testJoinWaitlist() {
        int prev = event.getWaitlist().getWaitList().size();

        userOperations.joinWaitlist((event.getEventID()).toString(), user.getUsername());

        int num = event.getWaitlist().getWaitList().size();
        assertEquals(prev + 1, num);
    }

    @Test
    public void testleaveWaitlist() {

        int prev = event.getWaitlist().getWaitList().size();

        userOperations.leaveWaitlist((event.getEventID()).toString(), user.getUsername());

        int num = event.getWaitlist().getWaitList().size();
        assertEquals(prev - 1, num);
    }

}