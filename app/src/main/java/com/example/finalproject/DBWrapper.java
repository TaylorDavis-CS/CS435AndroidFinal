package com.example.finalproject;

import android.database.Cursor;

public class DBWrapper {
    private Cursor cursor;

    public DBWrapper(Cursor cursor){
        this.cursor = cursor;
    }

    public String getDate(){
        int col = cursor.getColumnIndex("date");
        String date = cursor.getString(col);
        return date;
    }

    public String getTitle(){
        int col = cursor.getColumnIndex("title");
        String title = cursor.getString(col);
        return title;
    }

    public String getTime(){
        int col = cursor.getColumnIndex("time");
        String time = cursor.getString(col);
        return time;
    }

    public String getDescription(){
        int col = cursor.getColumnIndex("description");
        String desc = cursor.getString(col);
        return desc;
    }

}