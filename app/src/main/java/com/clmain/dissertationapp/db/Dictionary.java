package com.clmain.dissertationapp.db;

/**
 * Created by user on 03/03/2017.
 */

public class Dictionary {
    private int dictionaryId;
    private String title;
    private String description;
    private String imageLocation;

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        dictionaryId = dictionaryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        imageLocation = imageLocation;
    }

    public Dictionary() {

    }

}