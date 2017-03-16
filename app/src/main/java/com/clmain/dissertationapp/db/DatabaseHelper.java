package com.clmain.dissertationapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME = "climbingApp";

    //Table Names
    private static final String TABLE_DICTIONARY="dictionary";
    private static final String TABLE_DICTIONARY_TAGS = "dictionary_tags";
    private static final String TABLE_CLIMBING_LOGBOOK = "climbing_logbook";

    //TABLE_DICTIONARY Column Names
    private static final String COLUMN_ID_DICTIONARY="dictionary_id";
    private static final String COLUMN_TITLE="title";
    private static final String COLUMN_DESCRIPTION="description";
    private static final String COLUMN_IMAGE_LOCATION="image_location";

    //TABLE_DICTIONARY_TAGS Column Names
    private static final String COLUMN_ID_TAGS = "tag_id";
    private static final String COLUMN_TAGS="tags";

    //TABLE_CLIMBING_LOGBOOK Column Names
    private static final String COLUMN_ID_CLIMB_LOG = "climb_id";
    private static final String COLUMN_DATE="date";
    private static final String COLUMN_LOCATION="location";
    private static final String COLUMN_GRADE="grade";
    private static final String COLUMN_STYLE="style";
    private static final String COLUMN_COMMENTS="comments";

    //Table Creation SQL
    private static final String CREATE_TABLE_DICTIONARY="CREATE TABLE " +
            TABLE_DICTIONARY + "( "+
            COLUMN_ID_DICTIONARY + " INTEGER PRIMARY KEY, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DESCRIPTION+ " TEXT, " +
            COLUMN_IMAGE_LOCATION +" TEXT"+ ")";

    private static final String CREATE_TABLE_DICTIONARY_TAGS = "CREATE TABLE " +
            TABLE_DICTIONARY_TAGS + "( " +
            COLUMN_ID_TAGS + "INTEGER PRIMARY KEY, " +
            COLUMN_ID_DICTIONARY + "TEXT, " +
            COLUMN_TAGS + "TEXT" + ")";

    private static final String CREATE_TABLE_LOGBOOK = "CREATE TABLE " +
            TABLE_CLIMBING_LOGBOOK +" ( " +
            COLUMN_ID_CLIMB_LOG + " INTEGER PRIMARY KEY, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_GRADE + " TEXT, " +
            COLUMN_STYLE + " TEXT, " +
            COLUMN_COMMENTS + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DICTIONARY);
        db.execSQL(CREATE_TABLE_DICTIONARY_TAGS);
        db.execSQL((CREATE_TABLE_LOGBOOK));
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY_TAGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIMBING_LOGBOOK);
        onCreate(db);
    }

    private void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db!=null && db.isOpen()) {
            db.close();
        }
    }

    //Create, Read, Update, Delete Dictionary Entries
    public void createDictionaryEntry(Dictionary dictionary, DictionaryTags tags) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, dictionary.getTitle());
        values.put(COLUMN_DESCRIPTION, dictionary.getDescription());
        values.put(COLUMN_IMAGE_LOCATION, COLUMN_IMAGE_LOCATION);

        long row_id = db.insert(TABLE_DICTIONARY, null, values);

        //TODO: Implement Add Tags
        closeDB();

    }

    public List<Dictionary> readAllDictionaryEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DICTIONARY;

        Cursor c = db.rawQuery(query, null);
        List<Dictionary> dictionaries = new ArrayList<>();
        Dictionary dictionary = new Dictionary();

        if(c.moveToFirst()) {
            do {
                dictionary.setDictionaryId(c.getInt(c.getColumnIndex(COLUMN_ID_DICTIONARY)));
                dictionary.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                dictionary.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));
                dictionary.setImageLocation(c.getString(c.getColumnIndex(COLUMN_IMAGE_LOCATION)));
                dictionaries.add(dictionary);
            }while(c.moveToNext());
        }
        return dictionaries;
    }

    public Dictionary readDictionaryEntry(long entry_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DICTIONARY + " WHERE " + COLUMN_ID_DICTIONARY + " = " + entry_id;

        Cursor c = db.rawQuery(query, null);
        Dictionary dictionary = new Dictionary();
        if(c!=null) {
            c.moveToFirst();

            dictionary.setDictionaryId(c.getInt(c.getColumnIndex(COLUMN_ID_DICTIONARY)));
            dictionary.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
            dictionary.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));
            dictionary.setImageLocation(c.getString(c.getColumnIndex(COLUMN_IMAGE_LOCATION)));
        }
        return dictionary;
    }

    public void createDictionaryTagsEntry(DictionaryTags tags) {
        //TODO: Implement createDictionaryTagsEntry
    }

    public void updateDictionaryEntry(Dictionary dictionary, DictionaryTags tags) {
        //TODO: Implement updateDictionaryEntry
    }

    public void deleteDictionaryEntry(long dictionaryId) {
        //TODO: Implement deleteDictionaryEntry
    }

    public void deleteDictionaryEntry(String dictionaryTitle) {
        //TODO: Implement deleteDictionaryEntry
    }

    public void deleteAllDictionaryEntries() {
        db= this.getWritableDatabase();
        db.delete(TABLE_DICTIONARY, null, null);
        db.delete(TABLE_DICTIONARY_TAGS, null, null);

    }

    public void deleteDictionaryTagsEntry(long dictionaryId) {
        //TODO: Implement deleteDictionaryTagsEntry
    }

    //TODO implement CRUD for logbook table

    public void createClimbLogEntry(ClimbingLogbook log) {

    }

    public ClimbingLogbook readClimbLogEntry(long entry_id) {
        return new ClimbingLogbook();
    }

    public List<ClimbingLogbook> readAllClimbLogEntries() {
        return new ArrayList<ClimbingLogbook>();
    }

    public void updateClimbLogEntry(ClimbingLogbook log) {

    }

    public void deleteCimbLogEntry(long entry_id) {

    }

}
