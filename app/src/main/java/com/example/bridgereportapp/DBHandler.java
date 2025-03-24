package com.example.bridgereportapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


// This class will be used to handle SQLite queries and operations

public class DBHandler extends SQLiteOpenHelper{

    private static final String DB_NAME = "reportsdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bridgeReports";
    private static final String ID_COL = "id";
    private static final String BRIDGE_NAME_COL = "name";
    private static final String LOCATION_COL = "location";
    private static final String DATE_COL = "date";
    private static final String STATE_COL = "state";




public DBHandler(Context context){
  super(context, DB_NAME,null, DB_VERSION);


}

    // method to create sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // setting column names and table name which have been previously declared

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BRIDGE_NAME_COL + " TEXT,"
                + LOCATION_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + STATE_COL + " TEXT)";

        // calling execsql to create query
        db.execSQL(query);
    }

    // this method will be called to add new reports, passing in parameters entered by user
    public void addNewReport(String bridgeName, String bridgeLocation, String bridgeDate, String bridgeState) {

       // creating variable for database and making it writable
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // associating values with their columns/keys
        values.put(BRIDGE_NAME_COL, bridgeName);
        values.put(LOCATION_COL, bridgeLocation);
        values.put(DATE_COL, bridgeDate);
        values.put(STATE_COL, bridgeState);

        db.insert(TABLE_NAME, null, values);

        db.close();



    }



    // this method is used to read all the reports
    public ArrayList<ReportModal> readReports() {
        // creating a variable for database and making it readable
        SQLiteDatabase db = this.getReadableDatabase();

        // making a query to the database and displaying it with cursor
        Cursor cursorReports = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // creating an array list
        ArrayList<ReportModal> reportModalArrayList = new ArrayList<>();

        // moving cursor to the first position.
        if (cursorReports.moveToFirst()) {
            do {
                //  adding the data from cursor to our array list.
                reportModalArrayList.add(new ReportModal(cursorReports.getString(1),
                        cursorReports.getString(2),
                        cursorReports.getString(3),
                        cursorReports.getString(4)));
            } while (cursorReports.moveToNext());
        }
        // return array list
        cursorReports.close();
        return reportModalArrayList;
    }
    public void editReport(String initbridgeName, String bridgeName, String bridgeLocation,
                             String bridgeDate, String bridgeState) {

        // edit report method is similar to addReport method

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BRIDGE_NAME_COL, bridgeName);
        values.put(LOCATION_COL, bridgeLocation);
        values.put(DATE_COL, bridgeDate);
        values.put(STATE_COL, bridgeState);

        // calling the update method as opposed to insert method
        db.update(TABLE_NAME, values, "name=?", new String[]{initbridgeName});
        db.close();
    }

    // delete a single record
    public void deleteReport(String bridgeName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{bridgeName});
        db.close();
    }

    // delete all method
    public void deleteAll() {


        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
        db.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // checking if table exists already
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
