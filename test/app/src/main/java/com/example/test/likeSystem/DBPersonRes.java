package com.example.test.likeSystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBPersonRes extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBPersonRes(Context context) {
        super(context, "db_test_person", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {     //check whether database exist
        db.execSQL("CREATE TABLE IF NOT EXISTS favourite(" +
                "name TEXT," +
                "latitude TEXT," +
                "longitude TEXT," +
                "rating TEXT," +
                "location TEXT," +
                "open TEXT," +
                "phone TEXT REFERENCES customer(phone))");
    }

    @Override //delete old vision, create a new vision
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favourite");
        onCreate(db);
        db.execSQL("ALTER TABLE restaurant_new RENAME TO favourite");
    }

    public void addRes(String name, String latitude, String longitude, String rating, String location, String open, String phone) {//add user
        db.execSQL("INSERT OR REPLACE INTO  favourite(name,latitude,longitude,rating,location,open,phone) VALUES(?,?,?,?,?,?,?)", new Object[]{name, latitude, longitude, rating, location, open, phone});
    }

    public ArrayList<PersonLike> getAllData() {
        ArrayList<PersonLike> list = new ArrayList<PersonLike>(); //create a container
        Cursor cursor = db.query("favourite", null, null, null, null, null, null); //descending
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String lat = cursor.getString(cursor.getColumnIndex("latitude"));
            String lng = cursor.getString(cursor.getColumnIndex("longitude"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String rating = cursor.getString(cursor.getColumnIndex("rating"));
            String open = cursor.getString(cursor.getColumnIndex("open"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            list.add(new PersonLike(name, lat, lng, rating, location, open, phone));  //add to list
        }
        return list;
    }
}
