package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView textView1;

    public CustomViewHolder(View itemView) {
        super(itemView);
        textView1 = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void setData(Cursor c) {
        textView1.setText(c.getString(c.getColumnIndex("text")));
    }
}