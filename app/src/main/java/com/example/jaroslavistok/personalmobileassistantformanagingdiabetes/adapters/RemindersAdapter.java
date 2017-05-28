package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class RemindersAdapter extends ArrayAdapter<Reminder> {


    private static class ViewHolder {
        TextView name;
        TextView category;
        TextView alarmTime;
        Switch reminderToggle;
    }

    public RemindersAdapter(Context context, List<Reminder> reminders){
        super(context, R.layout.reminder_row, reminders);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Reminder reminder = getItem(position);
        final RemindersAdapter.ViewHolder viewHolder;


        final Context context = getContext();
        if (convertView == null) {
            viewHolder = new RemindersAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.reminder_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.reminder_name);
            viewHolder.category = (TextView) convertView.findViewById(R.id.reminder_category);
            viewHolder.alarmTime = (TextView) convertView.findViewById(R.id.alarm_time);
            viewHolder.reminderToggle = (Switch) convertView.findViewById(R.id.reminder_toggle);

            if (reminder.isActive()) {
                viewHolder.reminderToggle.setChecked(true);
            } else {
                viewHolder.reminderToggle.setChecked(false);
            }

            viewHolder.reminderToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.w("Switched", String.valueOf(b));
                    if (b) {
                        reminder.setActive(true);
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            Query query = reference.child("users").child(AlarmsManager.currentUserId).child("reminders").orderByChild("id").equalTo(reminder.getId());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                            reference.child("users").child(AlarmsManager.currentUserId).child("reminders").child(issue.getKey()).child("active").setValue(true);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    }
                    else {
                        reminder.setActive(false);
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("users").child(AlarmsManager.currentUserId).child("reminders").orderByChild("id").equalTo(reminder.getId());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                        reference.child("users").child(AlarmsManager.currentUserId).child("reminders").child(issue.getKey()).child("active").setValue(false);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RemindersAdapter.ViewHolder) convertView.getTag();
        }

        if (reminder != null)
            viewHolder.name.setText("Názov: " + reminder.getName());
        else
            viewHolder.name.setText("");

        if (reminder != null)
            viewHolder.category.setText("Kategória: " + reminder.getCategory());
        else
            viewHolder.category.setText("");

        if (reminder != null)
            viewHolder.alarmTime.setText("Čas: " + reminder.getAlarmTime());
        else
            viewHolder.alarmTime.setText("");

        return convertView;
    }
}
