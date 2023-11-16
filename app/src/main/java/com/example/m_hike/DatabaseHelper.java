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

    private static final String KEY_TABLE_OBSERVATION = "observation_table";
    private static final String KEY_OBSERVATION_ID = "id";
    private static final String KEY_OBSERVATION = "observation";
    private static final String KEY_OBSERVATION_TIME = "Time";
    private static final String KEY_HIKE_ID = "HikeID";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_M_Hike_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ID, KEY_NAME, KEY_LOCATION, KEY_DATE_OF_HIKE, KEY_PARKING_AVAILABLE, KEY_LENGTH_OF_HIKE, KEY_LEVEL_OF_DIFFICULT, KEY_DESCRIPTION);
        Log.d("table MHIKE: " ,"table has been create");
        String create_Observation_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER,  FOREIGN KEY(%s) REFERENCES %s (%s))",
                KEY_TABLE_OBSERVATION, KEY_OBSERVATION_ID, KEY_OBSERVATION, KEY_OBSERVATION_TIME, KEY_HIKE_ID,KEY_HIKE_ID,TABLE_NAME,KEY_ID);
        Log.d("table Observation: " ,"table has been create");
        db.execSQL(create_Observation_table);
        db.execSQL(create_M_Hike_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_M_Hike_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

        String drop_Observation_table = String.format("DROP TABLE IF EXISTS %s", KEY_TABLE_OBSERVATION);
        db.execSQL(drop_Observation_table);
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
    public long insertObservationData(String observation, String time, long HikeId)
    {
        ContentValues rowValue = new ContentValues();
        rowValue.put(KEY_OBSERVATION,observation);
        rowValue.put(KEY_OBSERVATION_TIME ,time);
        rowValue.put(KEY_HIKE_ID,HikeId);

        Log.d("table: " ,rowValue.toString());
        return db.insertOrThrow(KEY_TABLE_OBSERVATION, null, rowValue);
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

    public List<MHike> getData(String search){
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
//        String selection = "(" + DatabaseHelper.KEY_NAME + " LIKE ?) or  " + "(" +DatabaseHelper.KEY_LOCATION + " = ?) or  " + "(" +DatabaseHelper.KEY_LENGTH_OF_HIKE + " LIKE ?) or " + " ( " +DatabaseHelper.KEY_DATE_OF_HIKE + " LIKE ? )";
//       String[] selectionArgs = { search };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =

        Cursor cursor =  db.query(
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



            if(name.toUpperCase().contains(search.toUpperCase()) || location.toUpperCase().contains(search.toUpperCase()) ||dateOfHike.toUpperCase().contains(search.toUpperCase()) ||lengthOfHike.toUpperCase().contains(search.toUpperCase())) {
                MHIKE.add(new MHike(id, name, location, dateOfHike, parkingAvailable, lengthOfHike, levelOfDifficult, description));
            }
            cursor.moveToNext();
        }
        cursor.close();

        return MHIKE;
    }
    public MHike getHikeDetailByID(long ID) {
        MHike mhike = null;
        SQLiteDatabase db = getReadableDatabase();
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
        String[] selectionArgs = { ID + "" };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            String  location= cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION));
            String dateOfHike = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE_OF_HIKE));
            String parkingAvailable = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PARKING_AVAILABLE));
            String lengthOfHike = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LENGTH_OF_HIKE));
            String levelOfDifficult = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LEVEL_OF_DIFFICULT));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));

            mhike = new MHike(id,name,location,dateOfHike,parkingAvailable,lengthOfHike,levelOfDifficult,description);
        }
        cursor.close();
        return mhike;
    }

    public void UpdateHikeById(long ID,String name, String location, String dateOfHike, String parkingAvailable, String lengthOfHike, String levelOfDifficult, String description) {
        SQLiteDatabase db = getWritableDatabase();

// New value for one column
        ContentValues rowValue = new ContentValues();
        rowValue.put(KEY_ID,ID);
        rowValue.put(KEY_NAME,name);
        rowValue.put(KEY_LOCATION ,location);
        rowValue.put(KEY_DATE_OF_HIKE,dateOfHike);
        rowValue.put(KEY_PARKING_AVAILABLE,parkingAvailable);
        rowValue.put(KEY_LENGTH_OF_HIKE,lengthOfHike);
        rowValue.put(KEY_LEVEL_OF_DIFFICULT,levelOfDifficult);
        rowValue.put(KEY_DESCRIPTION,description);

// Which row to update, based on the title
        String selection = DatabaseHelper.KEY_ID + " = ?";
        String[] selectionArgs = { ID + ""};

        Log.d("HikeIdUpdate",Long.toString(ID));

        int count = db.update(
                DatabaseHelper.TABLE_NAME,
                rowValue,
                selection,
                selectionArgs);
    }


    void deleteHikeDetailByID(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM M_Hike where id = ?", new String[]{String.valueOf(ID)});
    }

    public List<ObservationModel> getobservationData(long HIKEID){
        String[] projection = {
                KEY_OBSERVATION_ID,
                DatabaseHelper.KEY_OBSERVATION,
                DatabaseHelper.KEY_OBSERVATION_TIME,
                DatabaseHelper.KEY_HIKE_ID,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = DatabaseHelper.KEY_HIKE_ID + " = ?";
        String[] selectionArgs = { HIKEID + "" };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =

        Cursor cursor = db.query(
                DatabaseHelper.KEY_TABLE_OBSERVATION,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.moveToFirst();
        List<ObservationModel> observationModels = new ArrayList<>();
        while(!cursor.isAfterLast()) {

            String observation = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OBSERVATION));
            long id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_OBSERVATION_ID));
            String  dateTime= cursor.getString(cursor.getColumnIndexOrThrow(KEY_OBSERVATION_TIME));

            observationModels.add(new ObservationModel(id,observation,dateTime));

            cursor.moveToNext();
        }
        cursor.close();

        return observationModels;
    }

    public ObservationModel getObservationById(long id){
        ObservationModel observationModel = null;

        String[] projection = {
                KEY_OBSERVATION_ID,
                DatabaseHelper.KEY_OBSERVATION,
                DatabaseHelper.KEY_OBSERVATION_TIME,
                DatabaseHelper.KEY_HIKE_ID,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = DatabaseHelper.KEY_OBSERVATION_ID + " = ?";
        String[] selectionArgs = { id +"" };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =

        Cursor cursor = db.query(
                DatabaseHelper.KEY_TABLE_OBSERVATION,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String observation = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OBSERVATION));
            String  dateTime= cursor.getString(cursor.getColumnIndexOrThrow(KEY_OBSERVATION_TIME));

            observationModel = new ObservationModel(id,observation,dateTime);
        }
        cursor.close();
        return observationModel;
    }

    void deleteObservationByID(long ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM observation_table where id = ?", new String[]{String.valueOf(ID)});
    }

    public void UpdateObservationById(long ID, String observation, String time, long HikeId) {
        SQLiteDatabase db = getWritableDatabase();

// New value for one column
        ContentValues rowValue = new ContentValues();
        rowValue.put(KEY_OBSERVATION,observation);
        rowValue.put(KEY_OBSERVATION_TIME ,time);
        rowValue.put(KEY_HIKE_ID,HikeId);

        Log.d("table: " ,rowValue.toString());

// Which row to update, based on the title
        String selection = DatabaseHelper.KEY_OBSERVATION_ID + " = ?";
        String[] selectionArgs = { ID + ""};

        int count = db.update(
                DatabaseHelper.KEY_TABLE_OBSERVATION,
                rowValue,
                selection,
                selectionArgs);
    }
}
