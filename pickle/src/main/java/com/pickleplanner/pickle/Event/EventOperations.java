
package com.pickleplanner.pickle.Event;

import com.pickleplanner.pickle.Event.Event;
import org.springframework.stereotype.Component;
import java.util.*;
import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.Event.Waitlist;

@Component
public class EventOperations {

    public void addToWaitlist(Waitlist list, User user) {
        list.getWaitList().add(user);
    }

    public void removeFromWaitlist(Waitlist list, User user) {
        list.getWaitList().remove(user);

    }

}
