package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;

public class MyHandlerThread extends HandlerThread {
    Context context;
    Handler mainHandler;
    ContentValues contentValues;
    DBHelper dbHelper;
    public MyHandlerThread(Context context, Handler mainHandler) {
        super("MyHandlerThread");
        this.context = context;
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        contentValues = new ContentValues();
        contentValues.put("date", NewReminderActivity.date);
        contentValues.put("time", NewReminderActivity.time);
        contentValues.put("title", NewReminderActivity.title);
        contentValues.put("description", NewReminderActivity.desc);
        dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.insert("reminders", null, contentValues);
        dbHelper.close();
    }
}
