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
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.alarm_example.SampleAlarmReceiver;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;

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

            viewHolder.reminderToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.w("Switched", String.valueOf(b));
                    if (b) {
                        reminder.setActive(true);
                        SampleAlarmReceiver alarm = new SampleAlarmReceiver();
                        alarm.setAlarm(context);
                        Log.w("seeee", "seeee");
                    }
                    else
                        reminder.setActive(false);
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RemindersAdapter.ViewHolder) convertView.getTag();
        }

        if (reminder != null)
            viewHolder.name.setText(reminder.getName());
        else
            viewHolder.name.setText("");

        if (reminder != null)
            viewHolder.category.setText(reminder.getCategory());
        else
            viewHolder.category.setText("");

        if (reminder != null)
            viewHolder.alarmTime.setText(reminder.getAlarmTime());
        else
            viewHolder.alarmTime.setText("");

        return convertView;
    }

}
