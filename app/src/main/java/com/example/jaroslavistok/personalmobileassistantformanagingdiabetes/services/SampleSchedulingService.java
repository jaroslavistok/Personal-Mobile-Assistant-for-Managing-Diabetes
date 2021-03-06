package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities.AlarmActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities.AlarmNotificationActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities.EntriesListActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers.SampleAlarmReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.util.Arrays;
import java.util.Calendar;

public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }
    public static final String TAG = "Scheduling Demo";
    public static final int NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("Notification");
        Intent alarmActivityIntent = new Intent(getApplicationContext(), AlarmNotificationActivity.class);
        alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmActivityIntent.putExtra("alarmId", intent.getIntExtra("alarmId", 0));
        getApplication().startActivity(alarmActivityIntent);
        SampleAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, AlarmNotificationActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.add_button_image)
                .setContentTitle("Active Alarm")
                .setContentIntent(contentIntent)
                .setShowWhen(false)
                .setAutoCancel(false)
                .setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM);

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
