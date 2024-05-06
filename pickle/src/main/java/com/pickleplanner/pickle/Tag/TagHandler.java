package com.pickleplanner.pickle.Tag;

import org.springframework.stereotype.Component;

@Component
public class TagHandler {
    private TagOperations tagOperations;

    public TagHandler() {
        tagOperations = new TagOperations();
    }

    public TagOperations getTagOperations() {
        return tagOperations;
    }

}