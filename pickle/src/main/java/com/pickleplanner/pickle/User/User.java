package com.pickleplanner.pickle.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class User {
    private String username;
    private String fname;
    private String lname;
    private String password;
    private String email;
    private String skillLevel;
    private Profile profile;
    private List<User> followingList;

    public User() {
    }

    public User(String fname, String lname, String username, String email, String password, String skillLevel) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.skillLevel = skillLevel;
        this.followingList = new ArrayList<User>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public Profile getProfile() {
        return profile;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

}
