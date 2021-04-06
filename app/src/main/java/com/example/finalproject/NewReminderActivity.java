package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText editTextTitle;
    EditText editTextDesc;
    TextView textViewDate;
    TextView textViewTime;
    static String date = "12/12/2020";
    static String time = "0:00";
    static String title;
    static String desc;
    DateItem dateItem;
    TimeItem timeItem;
    String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    String currentDay = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    String currentMonth = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
    String currentYear = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
    String day;
    String month;
    String year;
    int dayInt;
    int monthInt;
    int yearInt;
    NotificationManagerCompat notificationManager;
    MyHandlerThread myHandlerThread;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder_layout);
        editTextDesc = findViewById(R.id.editTextNewReminderDesc);
        editTextTitle = findViewById(R.id.editTextNewReminderTitle);
        textViewDate = findViewById(R.id.textViewNewReminderDateValue);
        textViewTime = findViewById(R.id.textViewNewReminderTimeValue);
        textViewDate.setText(currentDate);
        textViewTime.setText(time);
        year = currentDay;
        month = currentMonth;
        day = currentDay;
        date = currentDate;
        yearInt = Integer.parseInt(currentYear);
        monthInt = Integer.parseInt(currentMonth);
        dayInt = Integer.parseInt(currentDay);
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        mainHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    public void submit(View view) {
        title = editTextTitle.getText().toString();
        desc = editTextDesc.getText().toString();
        if (editTextTitle.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a Title for the Reminder", Toast.LENGTH_LONG).show();
        } else if (editTextDesc.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please add a Description for the Reminder", Toast.LENGTH_LONG).show();
        } else if (yearInt < Integer.parseInt(currentYear)) {
            Toast.makeText(getApplicationContext(), "Date Selected is Before Current Date Please Reselect", Toast.LENGTH_LONG).show();
        } else if (yearInt == Integer.parseInt(currentYear)) {
            if (monthInt < Integer.parseInt(currentMonth)) {
                Toast.makeText(getApplicationContext(), "Date Selected is Before Current Date Please Reselect", Toast.LENGTH_LONG).show();
            } else if (monthInt == Integer.parseInt(currentMonth)) {
                if (dayInt < Integer.parseInt(currentDay)) {
                    Toast.makeText(getApplicationContext(), "Date Selected is Before Current Date Please Reselect", Toast.LENGTH_LONG).show();
                } else {
                    finalSubmit();
                }
            } else {
                finalSubmit();
            }
        } else {
            finalSubmit();
        }
    }

    public void finalSubmit() {
        myHandlerThread = new MyHandlerThread(getApplicationContext(), mainHandler);
        myHandlerThread.start();
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), app.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentTitle("New Reminder Set")
                .setContentText("Reminder Set for: " + date + " at " + time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
        finish();
    }

    public void setTime(View view) {
        timeItem = new TimeItem(this);
        timeItem.show(getSupportFragmentManager(), "TIME");
    }

    public void setDate(View view) {
        dateItem = new DateItem(this);
        dateItem.show(getSupportFragmentManager(), "DATE");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        date = String.format("%d/%d/%d", m + 1, d, y);
        day = String.valueOf(d);
        month = String.valueOf(m + 1);
        year = String.valueOf(y);

        dayInt = d;
        monthInt = m + 1;
        yearInt = y;

        textViewDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time = String.format("%d:%02d", hourOfDay, minute, false);
        textViewTime.setText(time);
    }
}