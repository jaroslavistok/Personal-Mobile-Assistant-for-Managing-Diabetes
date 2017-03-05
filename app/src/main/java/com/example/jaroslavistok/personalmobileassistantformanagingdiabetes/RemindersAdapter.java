package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

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
        Reminder reminder = getItem(position);
        RemindersAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new RemindersAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.reminder_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.reminder_name);
            viewHolder.category = (TextView) convertView.findViewById(R.id.reminder_category);
            viewHolder.alarmTime = (TextView) convertView.findViewById(R.id.alarm_time);
            viewHolder.reminderToggle = (Switch) convertView.findViewById(R.id.reminder_toggle);
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

        if (reminder != null)
            viewHolder.reminderToggle.setChecked(reminder.isActive());
        else
            viewHolder.reminderToggle.setChecked(false);

        return convertView;
    }

}
