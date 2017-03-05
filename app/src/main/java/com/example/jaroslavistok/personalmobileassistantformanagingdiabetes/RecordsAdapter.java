package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        if (record != null)
            viewHolder.dateTime.setText(record.getDatetime());
        else
            viewHolder.dateTime.setText("");

        if (record != null)
            viewHolder.glucoseValue.setText(record.getGlucoseValue());
        else
            viewHolder.glucoseValue.setText("");

        if (record != null)
            viewHolder.category.setText(record.getCategory());
        else
            viewHolder.category.setText("");

        if (record != null)
            viewHolder.fastInsuline.setText(record.getFastInsuline());
        else
            viewHolder.fastInsuline.setText("");

        if (record != null)
            viewHolder.slowInsuline.setText(record.getSlowInsuline());
        else
            viewHolder.slowInsuline.setText("");

        if (record != null)
            viewHolder.note.setText(record.getNote());
        else
            viewHolder.note.setText("");

        if (record != null)
            viewHolder.carbs.setText(record.getCarbs());
        else
            viewHolder.carbs.setText("");

        Button deleteButton = (Button) convertView.findViewById(R.id.delete_record);
        Button updateButton = (Button) convertView.findViewById(R.id.update_record);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button_clicked", "clicked");

//                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//                mDatabase.child("users").child(mUserId).child("items")
//                        .orderByChild("title")
//                        .equalTo((String) listView.getItemAtPosition(position))
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.hasChildren()) {
//                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
//                                    firstChild.getRef().removeValue();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button_clicked", "clicked");
            }
        });
        return convertView;
    }
}