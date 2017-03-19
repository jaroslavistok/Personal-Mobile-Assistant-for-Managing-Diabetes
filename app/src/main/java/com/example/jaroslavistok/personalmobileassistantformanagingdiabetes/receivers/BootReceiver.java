package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities.HomeScreenActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            setUpListenerForSettingAlarms(context);
        }
    }

    private void setUpListenerForSettingAlarms(final Context context) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = firebaseUser.getUid();
        if (currentUserID != null) {

            databaseReference.child("users").child(currentUserID).child("reminders").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.w("onchildadded", "www");
                    if (dataSnapshot.exists()) {
                        Reminder reminder = dataSnapshot.getValue(Reminder.class);

                        String parsedTime[] = reminder.getAlarmTime().split(":");
                        int hour = Integer.valueOf(parsedTime[0]);
                        int minute = Integer.valueOf(parsedTime[1]);

                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        Calendar now = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
                        calendar.set(java.util.Calendar.MINUTE, minute);

                        long alarmMilis = 0;

                        if (calendar.getTimeInMillis() <= now.getTimeInMillis()) {
                            alarmMilis = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
                            Log.w("lower", "de");
                        } else {
                            alarmMilis = calendar.getTimeInMillis();
                            Log.w("higher", "cece");
                        }

                        if (reminder.isActive()) {
                            Intent intent = new Intent(context, SampleAlarmReceiver.class);
                            AlarmsManager.addAlarm(context, intent, Integer.valueOf(reminder.getId()), alarmMilis);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.exists()) {
                        Reminder reminder = dataSnapshot.getValue(Reminder.class);

                        String parsedTime[] = reminder.getAlarmTime().split(":");
                        int hour = Integer.valueOf(parsedTime[0]);
                        int minute = Integer.valueOf(parsedTime[1]);

                        java.util.Calendar calendar = java.util.Calendar.getInstance();
                        Calendar now = Calendar.getInstance();

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
                        calendar.set(java.util.Calendar.MINUTE, minute);

                        long alarmMilis = 0;

                        if (calendar.getTimeInMillis() <= now.getTimeInMillis()) {
                            alarmMilis = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
                            Log.w("lower", "de");
                        } else {
                            alarmMilis = calendar.getTimeInMillis();
                            Log.w("higher", "Dedede");
                        }


                        Intent intent = new Intent(context, SampleAlarmReceiver.class);
                        if (reminder.isActive()) {

                            AlarmsManager.cancelAlarm(context, intent, Integer.valueOf(reminder.getId()));
                            AlarmsManager.addAlarm(context, intent, Integer.valueOf(reminder.getId()), alarmMilis);
                            Log.w("higher", AlarmsManager.getAlarmIds(context).toString());
                        } else {
                            AlarmsManager.cancelAlarm(context, intent, Integer.valueOf(reminder.getId()));
                        }
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Intent intent = new Intent(context, SampleAlarmReceiver.class);
                        Reminder reminder = dataSnapshot.getValue(Reminder.class);
                        if (reminder.isActive()) {
                            AlarmsManager.cancelAlarm(context, intent, Integer.valueOf(reminder.getId()));
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
