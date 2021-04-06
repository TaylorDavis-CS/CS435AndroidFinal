package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    DBWrapper dbWrapper;
    Context context;
    Listener listener;

    public RecyclerViewAdapter(Context context){
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("reminders", new String[]{"_id","date","time","title", "description"}, null, null, null, null, null);
        this.context = context;
    }

    public interface Listener {
        public void recycleClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewTime;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.linearLayout = itemView.findViewById(R.id.recyclerViewLinearLayout);
            this.textViewTitle = itemView.findViewById(R.id.recyclerViewTitle);
            this.textViewDate = itemView.findViewById(R.id.recyclerViewDate);
            this.textViewTime = itemView.findViewById(R.id.recyclerViewTime);
        }
        public LinearLayout getLinearLayout(){
            return this.linearLayout;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        dbWrapper = new DBWrapper(cursor);
        cursor.moveToPosition(position);
        String title = dbWrapper.getTitle();
        String date = dbWrapper.getDate();
        String time = dbWrapper.getTime();
        recyclerViewHolder.textViewTitle.setText(title);
        recyclerViewHolder.textViewDate.setText(date);
        recyclerViewHolder.textViewTime.setText(time);
        int color = position % 2;
        LinearLayout linearLayout = recyclerViewHolder.getLinearLayout();
        if(color == 0){
           recyclerViewHolder.linearLayout.setBackgroundColor(Color.rgb(224, 156, 86));
        }
        else{
            recyclerViewHolder.linearLayout.setBackgroundColor(Color.rgb(224, 156, 215));
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.recycleClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query("reminders", new String[]{"_id","date","time","title", "description"}, null, null, null, null, null);
        return cursor.getCount();
    }
}
