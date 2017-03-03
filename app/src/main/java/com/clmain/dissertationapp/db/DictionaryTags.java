package com.clmain.dissertationapp.db;

/**
 * Created by user on 03/03/2017.
 */

public class DictionaryTags {

    private int tagId;
    private int dictionaryId;
    private String Tag;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }



    public DictionaryTags() {

    }
}
