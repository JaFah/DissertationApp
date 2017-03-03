package com.clmain.dissertationapp.db;

/**
 * Created by user on 03/03/2017.
 */

public class Dictionary {
    private int DictionaryId;
    private String name;
    private String Description;
    private String ImageLocation;

    public int getDictionaryId() {
        return DictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        DictionaryId = dictionaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageLocation() {
        return ImageLocation;
    }

    public void setImageLocation(String imageLocation) {
        ImageLocation = imageLocation;
    }

    public Dictionary() {

    }

}