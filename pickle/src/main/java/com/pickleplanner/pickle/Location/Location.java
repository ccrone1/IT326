package com.pickleplanner.pickle.Location;

public class Location {
    private String street;
    private String city;
    private String zip;
    private String state;

    private String location;

    // Constructor
    public Location(String location) {
        this.location = location;
    }

    public Location(String street, String city, String zip, String state) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }

    // Getter and setter methods
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Method to change location
    public void changeLocation(String street, String city, String zip, String state) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.state = state;
    }

    // Method to display location
    public void displayLocation() {
        System.out.println("Street: " + street);
        System.out.println("City: " + city);
        System.out.println("ZIP Code: " + zip);
        System.out.println("State: " + state);
    }

}
