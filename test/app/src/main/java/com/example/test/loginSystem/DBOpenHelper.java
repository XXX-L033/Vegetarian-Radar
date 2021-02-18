package com.example.test.loginSystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelper(Context context) {
        super(context, "db_test_new", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {     //check whether database exist
        db.execSQL("CREATE TABLE IF NOT EXISTS customer(" +
                "name TEXT," +
                "password TEXT," +
                "phone TEXT  PRIMARY KEY)");
    }

    @Override //delete old vision, create a new vision
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS customer");
        onCreate(db);
    }

    public void addUser(String name, String password, String phone) {//add user
        db.execSQL("INSERT INTO customer (name,password,phone) VALUES(?,?,?)", new Object[]{name, password, phone});
    }

    public void updateUser(String phone, String password) { //forget password--update
        db.execSQL("UPDATE customer SET password = ? WHERE phone = ?", new Object[]{password, phone});
    }

    /**
     * check the whole table
     * ArrayList--
     * while
     */
    public ArrayList<User> getAllData() {
        ArrayList<User> list = new ArrayList<User>(); //create a container
        Cursor cursor = db.query("customer", null, null, null, null, null, "name DESC"); //descending
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            list.add(new User(name, password, phone));  //add to list
        }
        return list;
    }
}

