package com.pickleplanner.pickle.Search_Filter;

public class Filter {

    // Constants
    public static final int MIN_CRITERIA = 0;
    public static final int MAX_CRITERIA = -1; // Unlimited

    // Properties
    private int minCriteria;
    private int maxCriteria;
    private boolean contains;

    // Constructor
    public Filter() {
        this.minCriteria = MIN_CRITERIA;
        this.maxCriteria = MAX_CRITERIA;
        this.contains = false;
    }

    // Methods

    /**
     * Setter for minCriteria.
     * 
     * @param minCriteria The minimum criteria to set.
     */
    public void setMinCriteria(int minCriteria) {
        this.minCriteria = minCriteria;
    }

    /**
     * Getter for minCriteria.
     * 
     * @return The minimum criteria.
     */
    public int getMinCriteria() {
        return minCriteria;
    }

    /**
     * Setter for maxCriteria.
     * 
     * @param maxCriteria The maximum criteria to set.
     */
    public void setMaxCriteria(int maxCriteria) {
        this.maxCriteria = maxCriteria;
    }

    /**
     * Getter for maxCriteria.
     * 
     * @return The maximum criteria.
     */
    public int getMaxCriteria() {
        return maxCriteria;
    }

    /**
     * Setter for contains.
     * 
     * @param contains True if filter contains criteria, otherwise false.
     */
    public void setContains(boolean contains) {
        this.contains = contains;
    }

    /**
     * Getter for contains.
     * 
     * @return True if filter contains criteria, otherwise false.
     */
    public boolean isContains() {
        return contains;
    }
}
