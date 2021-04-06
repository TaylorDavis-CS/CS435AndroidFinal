package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class RecyclerViewIntent extends AppCompatActivity {
    TextView textViewTitle;
    TextView textViewDesc;
    TextView textViewDate;
    TextView textViewTime;
    int position;
    DBHelper dbHelper;
    String title;
    String desc;
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recycler_view_intent);
        textViewTitle = findViewById(R.id.textViewNewIntentTitle);
        textViewDesc = findViewById(R.id.textViewNewIntentDescription);
        textViewDate = findViewById(R.id.textViewNewIntentDate);
        textViewTime = findViewById(R.id.textViewNewIntentTime);
        position = getIntent().getIntExtra("position", 0);
        dbHelper = new DBHelper(getApplicationContext());
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("reminders", new String[]{"_id","date","time","title", "description"}, null, null, null, null, null);
        cursor.moveToPosition(position);
        DBWrapper dbWrapper = new DBWrapper(cursor);
        title = dbWrapper.getTitle();
        desc = dbWrapper.getDescription();
        date = dbWrapper.getDate();
        time = dbWrapper.getTime();
        textViewTitle.setText(title);
        textViewDesc.setText(desc);
        textViewDate.setText(date);
        textViewTime.setText(time);
    }
}