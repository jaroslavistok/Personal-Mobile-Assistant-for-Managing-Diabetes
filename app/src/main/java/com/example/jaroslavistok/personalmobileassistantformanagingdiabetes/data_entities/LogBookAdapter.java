package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;

import java.util.Collections;
import java.util.List;


public class LogBookAdapter extends CursorRecyclerViewAdapter<LogBookRowViewHolder> {

    List<LogBookRowData> list = Collections.emptyList();
    Context context;

    public LogBookAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }

    @Override
    public long getItemId(int position){
        return super.getItemId(position);
    }

    @Override
    public LogBookRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        LogBookRowViewHolder viewHolder = new LogBookRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public void onBindViewHolder(LogBookRowViewHolder viewHolder, Cursor cursor) {
        cursor.moveToPosition(cursor.getPosition());
        viewHolder.setData(cursor);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void remove(LogBookRowData data){
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
