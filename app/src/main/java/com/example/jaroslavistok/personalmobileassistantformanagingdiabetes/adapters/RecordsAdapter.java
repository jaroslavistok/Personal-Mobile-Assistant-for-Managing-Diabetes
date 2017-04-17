package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Record;

import java.util.ArrayList;

public class RecordsAdapter extends ArrayAdapter<Record> implements Filterable {

    @NonNull
    @Override
    public Filter getFilter(){


        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };


        return null;
    }

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
            viewHolder.note = (TextView) convertView.findViewById(R.id.note);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (record != null)
            viewHolder.dateTime.append(record.getDate() + "\n" + record.getTime());
        else
            viewHolder.dateTime.setText("");

        if (record != null)
            viewHolder.glucoseValue.append(record.getGlucoseValue());
        else
            viewHolder.glucoseValue.setText("");

        if (record != null)
            viewHolder.category.append( record.getCategory());
        else
            viewHolder.category.setText("");

        if (record != null)
            viewHolder.fastInsuline.append(record.getFastInsuline());
        else
            viewHolder.fastInsuline.setText("");

        if (record != null)
            viewHolder.slowInsuline.append(record.getSlowInsuline());
        else
            viewHolder.slowInsuline.setText("");

        if (record != null)
            viewHolder.note.append( record.getNote());
        else
            viewHolder.note.setText("");

        return convertView;
    }
}