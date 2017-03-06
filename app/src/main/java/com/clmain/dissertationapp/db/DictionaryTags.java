package com.clmain.dissertationapp.db;

import java.util.ArrayList;

/**
 * Created by user on 03/03/2017.
 */

public class DictionaryTags {

    private ArrayList<Integer> tagId;
    private int dictionaryId;

    public ArrayList<String> getTag() {
        return Tag;
    }

    public void setTag(ArrayList<String> tag) {
        Tag = tag;
    }

    public ArrayList<Integer> getTagId() {
        return tagId;
    }

    public void setTagId(ArrayList<Integer> tagId) {
        this.tagId = tagId;
    }

    private ArrayList<String> Tag;

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public DictionaryTags() {

    }
}
