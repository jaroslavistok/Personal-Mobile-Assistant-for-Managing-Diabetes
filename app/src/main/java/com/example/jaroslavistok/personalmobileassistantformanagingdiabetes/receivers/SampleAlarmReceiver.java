package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.DiabetesApplication;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.services.SampleSchedulingService;

import java.util.Calendar;

import static android.content.Context.VIBRATOR_SERVICE;

public class SampleAlarmReceiver extends WakefulBroadcastReceiver {

    private static final long DAY_IN_MILISECONDS = 86400000L;
    private static final long MINUTE_IN_MILISECONDS  = 60000L;
    private static final int VIBRATION_DURATION = 2500;

    @Override
    public void onReceive(Context context, Intent intent) {
        DiabetesApplication.getInstance().isRinging = true;
        Intent service = new Intent(context, SampleSchedulingService.class);
        service.putExtra("alarmId", intent.getIntExtra("alarmId", 0));
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATION_DURATION);
        Calendar now = Calendar.getInstance();
        AlarmsManager.addAlarm(context, intent, intent.getIntExtra("alarmId",0), now.getTimeInMillis() + DAY_IN_MILISECONDS);
        Log.w("Active alarms IDs", String.valueOf(AlarmsManager.getAlarmIds(context)));
        startWakefulService(context, service);
    }
}
