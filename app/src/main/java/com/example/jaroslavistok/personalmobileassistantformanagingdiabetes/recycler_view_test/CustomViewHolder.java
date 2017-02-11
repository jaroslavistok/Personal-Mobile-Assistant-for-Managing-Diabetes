package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView category;
    public TextView glucose;
    public TextView date;

    public CustomViewHolder(View itemView) {
        super(itemView);
        category = (TextView) itemView.findViewById(R.id.category);
        glucose = (TextView) itemView.findViewById(R.id.glucose);
        date = (TextView) itemView.findViewById(R.id.date);
    }

    public void setData(Cursor cursor) {
        category.setText(cursor.getString(cursor.getColumnIndex(EntriesTableContract.CATEGORY)));
        glucose.setText(cursor.getString(cursor.getColumnIndex(EntriesTableContract.GLUCOSE_VALUE)));
        date.setText(cursor.getString(cursor.getColumnIndex(EntriesTableContract.DATE)));
    }
}