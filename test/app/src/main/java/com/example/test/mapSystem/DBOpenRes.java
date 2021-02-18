package com.example.test.mapSystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenRes extends SQLiteOpenHelper { //database store restaurant information
    private SQLiteDatabase db;

    public DBOpenRes(Context context) {
        super(context, "db_test_vege", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {     //create database
        db.execSQL("CREATE TABLE IF NOT EXISTS restaurant(" +
                "name TEXT," +
                "latitude TEXT," +
                "longitude TEXT," +
                "distance TEXT," +
                "code TEXT," +
                "rating TEXT," +
                "location TEXT PRIMARY KEY," +
                "open TEXT)");
    }

    @Override //delete old vision, create a new vision
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS restaurant");
        onCreate(db);
        db.execSQL("ALTER TABLE restaurant_new RENAME TO restaurant");
    }

    public void addRes(String name, String latitude, String longitude, String distance, String code, String rating, String location, String open) {//add user
        db.execSQL("INSERT OR REPLACE INTO restaurant (name,latitude,longitude,distance,code,rating,location,open) VALUES(?,?,?,?,?,?,?,?)", new Object[]{name, latitude, longitude, distance, code, rating, location, open});
    }

    public ArrayList<VegeRestaurant> getAllData() { //store all the data in database in an arraylist
        ArrayList<VegeRestaurant> list = new ArrayList<VegeRestaurant>(); //create a container
        Cursor cursor = db.query("restaurant", null, null, null, null, null, "rating DESC"); //descending
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String code = cursor.getString(cursor.getColumnIndex("code"));
            String lat = cursor.getString(cursor.getColumnIndex("latitude"));
            String lng = cursor.getString(cursor.getColumnIndex("longitude"));
            String distance = cursor.getString(cursor.getColumnIndex("distance"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String rating = cursor.getString(cursor.getColumnIndex("rating"));
            String open = cursor.getString(cursor.getColumnIndex("open"));
            list.add(new VegeRestaurant(name, lat, lng, distance, code, rating, location, open));  //add to list
        }
        return list;
    }

    public ArrayList<VegeRestaurant> getAllDataInDis() {
        ArrayList<VegeRestaurant> list = new ArrayList<VegeRestaurant>(); //create a container
        Cursor cursor = db.query("restaurant", null, null, null, null, null, "distance ASC"); //ascending
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String code = cursor.getString(cursor.getColumnIndex("code"));
            String lat = cursor.getString(cursor.getColumnIndex("latitude"));
            String lng = cursor.getString(cursor.getColumnIndex("longitude"));
            String distance = cursor.getString(cursor.getColumnIndex("distance"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String rating = cursor.getString(cursor.getColumnIndex("rating"));
            String open = cursor.getString(cursor.getColumnIndex("open"));
            list.add(new VegeRestaurant(name, lat, lng, distance, code, rating, location, open));  //add to list
        }
        return list;
    }
}
