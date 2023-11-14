package com.example.m_hike;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Hike_Manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "M_Hike";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DATE_OF_HIKE = "dateOfHike";
    private static final String KEY_PARKING_AVAILABLE = "parkingAvailable";
    private static final String KEY_LENGTH_OF_HIKE = "lengthOfHike";
    private static final String KEY_LEVEL_OF_DIFFICULT = "levelOfDifficult";
    private static final String KEY_DESCRIPTION = "description";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_M_Hike_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, KEY_NAME, KEY_LOCATION, KEY_DATE_OF_HIKE, KEY_PARKING_AVAILABLE, KEY_LENGTH_OF_HIKE, KEY_LEVEL_OF_DIFFICULT, KEY_DESCRIPTION);
        Log.d("table: " ,"table has been create");
        db.execSQL(create_M_Hike_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_M_Hike_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_M_Hike_table);

        onCreate(db);
    }

    public long insertData(String name, String location, String dateOfHike, String parkingAvailable, String lengthOfHike, String levelOfDifficult, String description)
    {
        ContentValues rowValue = new ContentValues();
        rowValue.put(KEY_NAME,name);
        rowValue.put(KEY_LOCATION ,location);
        rowValue.put(KEY_DATE_OF_HIKE,dateOfHike);
        rowValue.put(KEY_PARKING_AVAILABLE,parkingAvailable);
        rowValue.put(KEY_LENGTH_OF_HIKE,lengthOfHike);
        rowValue.put(KEY_LEVEL_OF_DIFFICULT,levelOfDifficult);
        rowValue.put(KEY_DESCRIPTION,description);

        Log.d("table: " ,rowValue.toString());
        return db.insertOrThrow(TABLE_NAME, null, rowValue);
    }

    public List<MHike> getData(){
        String[] projection = {
               KEY_ID,
                DatabaseHelper.KEY_NAME,
                DatabaseHelper.KEY_DESCRIPTION,
                DatabaseHelper.KEY_LENGTH_OF_HIKE,
                DatabaseHelper.KEY_DATE_OF_HIKE,
                DatabaseHelper.KEY_LOCATION,
                DatabaseHelper.KEY_PARKING_AVAILABLE,
                DatabaseHelper.KEY_LEVEL_OF_DIFFICULT
        };

// Filter results WHERE "title" = 'My Title'
        String selection = DatabaseHelper.KEY_ID + " = ?";
        //String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToFirst();
        List<MHike> MHIKE = new ArrayList<>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            String  location= cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION));
            String dateOfHike = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE_OF_HIKE));
            String parkingAvailable = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PARKING_AVAILABLE));
            String lengthOfHike = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LENGTH_OF_HIKE));
            String levelOfDifficult = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LEVEL_OF_DIFFICULT));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));

            MHIKE.add(new MHike(id,name,location,dateOfHike,parkingAvailable,lengthOfHike,levelOfDifficult,description));

            cursor.moveToNext();
        }
        cursor.close();

        return MHIKE;
    }
}
