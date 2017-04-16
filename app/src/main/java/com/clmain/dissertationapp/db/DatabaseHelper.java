package com.clmain.dissertationapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Helper class is used by the Application to create and interact with an SQLite Database
 * @Author Connor Main
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int DATABASE_VERSION=3;

    private static final String DATABASE_NAME = "climbingApp";

    //Table Names
    private static final String TABLE_CLIMBING_LOGBOOK = "climbing_logbook";

    //TABLE_CLIMBING_LOGBOOK Column Names
    private static final String COLUMN_ID_CLIMB_LOG = "climb_id";
    private static final String COLUMN_DATE="date";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_LOCATION="location";
    private static final String COLUMN_GRADE="grade";
    private static final String COLUMN_STYLE="style";
    private static final String COLUMN_COMMENTS="comments";

    //Table Creation SQL
    private static final String CREATE_TABLE_LOGBOOK = "CREATE TABLE " +
            TABLE_CLIMBING_LOGBOOK +"( " +
            COLUMN_ID_CLIMB_LOG + " INTEGER PRIMARY KEY, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_LOCATION + " TEXT, " +
            COLUMN_GRADE + " TEXT, " +
            COLUMN_STYLE + " TEXT, " +
            COLUMN_COMMENTS + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called on instance creation. Creates Tables in Database
     * @param db SQLite Database to create tables in
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL((CREATE_TABLE_LOGBOOK));
    }

    /**
     * Called when databse is updated. Drops tables and recreates them by calling onCreate.
     * @param db SQLite Database
     * @param oldVersion previous version number
     * @param newVersion new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIMBING_LOGBOOK);
        onCreate(db);
    }

    /**
     * Closes the Database connection
     */
    private void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db!=null && db.isOpen()) {
            db.close();
        }
    }

    //Create, Read, Update, Delete Climb Log Entries

    //Create

    /**
     * Creates a single Climb Log entry in the database.
     *
     * @param log instance containing all the log information to upload to database
     */
    public void createClimbLogEntry(ClimbingLogbook log) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, log.getDate());
        values.put(COLUMN_NAME, log.getName());
        values.put(COLUMN_LOCATION, log.getLocation());
        values.put(COLUMN_STYLE, log.getStyle());
        values.put(COLUMN_GRADE, log.getGrade());
        values.put(COLUMN_COMMENTS, log.getComments());
        db.insert(TABLE_CLIMBING_LOGBOOK, null, values);
        closeDB();
    }

    //Read

    /**
     * Retrieves Entry from database
     *
     * @param entry_id id to retreive from database
     * @return ClimbingLogbook containing retreived data
     */
    public ClimbingLogbook readClimbLogEntry(long entry_id) {
        String query = "SELECT * FROM " + TABLE_CLIMBING_LOGBOOK + " WHERE " + COLUMN_ID_CLIMB_LOG + " = " + (entry_id);

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        ClimbingLogbook log = new ClimbingLogbook();

        if(c!=null) {
            c.moveToFirst();
        }
            log.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
            log.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
            log.setLocation(c.getString(c.getColumnIndex(COLUMN_LOCATION)));
            log.setStyle(c.getString(c.getColumnIndex(COLUMN_STYLE)));
            log.setGrade(c.getString(c.getColumnIndex(COLUMN_GRADE)));
            log.setComments(c.getString(c.getColumnIndex(COLUMN_COMMENTS)));

        db.close();

        return log;
    }

    /**
     * retreives all climb logs from database
     * @return List of climb logs in database
     */
    public List<ClimbingLogbook> readAllClimbLogEntries() {
        List<ClimbingLogbook> logs = new ArrayList<ClimbingLogbook>();
        String query = "SELECT * FROM " + TABLE_CLIMBING_LOGBOOK;

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                ClimbingLogbook log = new ClimbingLogbook();
                log.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                log.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                log.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                log.setLocation(c.getString(c.getColumnIndex(COLUMN_LOCATION)));
                log.setStyle(c.getString(c.getColumnIndex(COLUMN_STYLE)));
                log.setGrade(c.getString(c.getColumnIndex(COLUMN_GRADE)));
                log.setComments(c.getString(c.getColumnIndex(COLUMN_COMMENTS)));

                logs.add(log);

            }while(c.moveToNext());
        }
        db.close();
        return logs;
    }

    //Update

    /**
     * Updates a previously existing database entry
     * @param log New Log, with ID of log to replace
     */
    public void updateClimbLogEntry(ClimbingLogbook log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, log.getDate());
        values.put(COLUMN_NAME, log.getName());
        values.put(COLUMN_LOCATION, log.getLocation());
        values.put(COLUMN_STYLE, log.getStyle());
        values.put(COLUMN_GRADE, log.getGrade());
        values.put(COLUMN_COMMENTS, log.getComments());

        String id_row = COLUMN_ID_CLIMB_LOG+"=" + log.getID();
        db.update(TABLE_CLIMBING_LOGBOOK, values, id_row, null);
        closeDB();
    }

    //Delete

    /**
     * Empties all Climb Log data from database
     */
    public void deleteAllClimbLogEntries() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_CLIMBING_LOGBOOK);
        db.close();
    }

    /**
     * Removes a specific Climb Log column from
     * @param entry_id ID of log to remove
     */
    public void deleteCimbLogEntry(long entry_id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_CLIMBING_LOGBOOK + " WHERE " + COLUMN_ID_CLIMB_LOG + " = " + (entry_id+1);
        System.out.println("Query: " + query);
        db.execSQL(query);
        db.close();
    }

}
