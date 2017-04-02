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
import java.util.List;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){

            List<Integer> alarmIds = AlarmsManager.getAlarmIds(context);
            for (int i : alarmIds){
              // AlarmsManager.addAlarm(context, intent, i);
            }

        }
    }

}
