package com.pickleplanner.pickle.Event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pickleplanner.pickle.User.User;
import com.pickleplanner.pickle.Event.Waitlist;

@Component
public class Waitlist {
    private List<User> waitList;;

    public List<User> getWaitList() {
        return waitList;
    }

    public Waitlist() {
        this.waitList = null;
    }

}