package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.DiabetesApplication;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.services.SampleSchedulingService;

import java.util.Calendar;
import static android.content.Context.VIBRATOR_SERVICE;

public class SampleAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DiabetesApplication.getInstance().isRinging = true;
        Intent service = new Intent(context, SampleSchedulingService.class);
        service.putExtra("alarmId", intent.getIntExtra("alarmId", 0));
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Calendar now = Calendar.getInstance();
        AlarmsManager.addAlarm(context, intent, intent.getIntExtra("alarmId",0), now.getTimeInMillis() + 30000L);
        startWakefulService(context, service);
    }
}
