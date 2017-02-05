package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;


public class LogBookRowViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView category;
    public TextView glucose;
    public TextView date;
    private Cursor data;

    public LogBookRowViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        category = (TextView) itemView.findViewById(R.id.category);
        glucose = (TextView) itemView.findViewById(R.id.glucose);
        date = (TextView) itemView.findViewById(R.id.date);
    }

    public void setData(Cursor data) {
        this.data = data;
        category.setText(data.getColumnIndex("category"));
        glucose.setText(data.getColumnIndex("glucose"));
        date.setText(data.getColumnIndex("date"));
    }
}
