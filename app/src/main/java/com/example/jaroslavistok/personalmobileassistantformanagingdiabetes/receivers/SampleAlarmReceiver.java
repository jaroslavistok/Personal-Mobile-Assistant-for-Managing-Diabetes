package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.annotation.RequiresApi;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.services.SampleSchedulingService;

import java.util.Calendar;
import java.util.Date;

public class SampleAlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, SampleSchedulingService.class);
        service.putExtra("alarmId", intent.getIntExtra("alarmId", 0));
        startWakefulService(context, service);
    }
}
