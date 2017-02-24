package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordsAdapter extends ArrayAdapter<Record> {
    // View lookup cache
    private static class ViewHolder {
        TextView dateTime;
        TextView glucoseValue;
        TextView category;
        TextView fastInsuline;
        TextView slowInsuline;
        TextView carbs;
        TextView note;
    }

    public RecordsAdapter(Context context, ArrayList<Record> users) {
        super(context, R.layout.record_row, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.record_row, parent, false);
            viewHolder.dateTime = (TextView) convertView.findViewById(R.id.datetime);
            viewHolder.glucoseValue = (TextView) convertView.findViewById(R.id.glucose);
            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.fastInsuline = (TextView) convertView.findViewById(R.id.fast_insuline);
            viewHolder.slowInsuline = (TextView) convertView.findViewById(R.id.slow_insuline);
            viewHolder.carbs = (TextView) convertView.findViewById(R.id.carbs);
            viewHolder.note = (TextView) convertView.findViewById(R.id.note);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dateTime.setText(record.getDatetime());
        viewHolder.glucoseValue.setText(record.getGlucoseValue());
        viewHolder.category.setText(record.getCategory());
        viewHolder.fastInsuline.setText(record.getFastInsuline());
        viewHolder.slowInsuline.setText(record.getSlowInsuline());
        viewHolder.note.setText(record.getNote());
        viewHolder.carbs.setText(record.getCarbs());


        // Return the completed view to render on screen
        return convertView;
    }
}