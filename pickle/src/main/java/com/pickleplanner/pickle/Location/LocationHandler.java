package com.pickleplanner.pickle.Location;

import org.springframework.stereotype.Component;

@Component
public class LocationHandler {
    private LocationOperations locationOperations;

    public LocationHandler() {
        locationOperations = new LocationOperations();
    }

    public LocationOperations getLocationOperations() {
        return locationOperations;
    }
}