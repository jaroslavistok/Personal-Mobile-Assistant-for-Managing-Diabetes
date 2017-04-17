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
import java.util.List;

public class RecordsAdapter extends ArrayAdapter<Record> implements Filterable {


    public List<Record> getItems(){
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < getCount(); i++){
            records.add(getItem(i));
        }
        return records;
    }

    @NonNull
    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Record> records = getItems();

                String filterValue = charSequence.toString();

                //TODO filter results
                FilterResults filterResults = new FilterResults();
                filterResults.values = records;
                filterResults.count = records.size();
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        };
        return filter;
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
            viewHolder.dateTime.setText("Dátum a čas: " + record.getDate() + "\n" + record.getTime());
        else
            viewHolder.dateTime.setText("");

        if (record != null)
            viewHolder.glucoseValue.setText("Hodnota glykémie: " + record.getGlucoseValue());
        else
            viewHolder.glucoseValue.setText("");

        if (record != null)
            viewHolder.category.setText("Kategória: " + record.getCategory());
        else
            viewHolder.category.setText("");

        if (record != null)
            viewHolder.fastInsuline.setText("Rýchly inzulín: " + record.getFastInsuline());
        else
            viewHolder.fastInsuline.setText("");

        if (record != null)
            viewHolder.slowInsuline.setText("Pomalý inzulín: " + record.getSlowInsuline());
        else
            viewHolder.slowInsuline.setText("");

        if (record != null)
            viewHolder.note.setText("Poznámka: " + record.getNote());
        else
            viewHolder.note.setText("");

        return convertView;
    }
}