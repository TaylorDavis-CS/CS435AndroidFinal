package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Listener {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DBHelper dbHelper;
    NotificationManagerCompat notificationManager;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dbHelper = new DBHelper(getApplicationContext());
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDelete:
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.delete("reminders", null, null);
                recyclerViewAdapter.notifyDataSetChanged();
                deleteNotification();
                break;
            case R.id.menuAdd:
                Intent intent = new Intent(getApplicationContext(), NewReminderActivity.class);
                startActivity(intent);
                recyclerViewAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recycleClick(int position) {
        Intent intent = new Intent(getApplicationContext(), RecyclerViewIntent.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void deleteNotification(){
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), app.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_delete)
                .setContentTitle("Deleting")
                .setContentText("Deleting all current Reminders")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }
}