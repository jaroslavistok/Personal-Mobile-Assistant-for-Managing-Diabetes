package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AlarmsManager {

    private static final String ALARMS_TAG = ":alarms";
    public static int lastAlarmId = 0;
    public static String currentUserId = "";

    public static void addAlarm(Context context, Intent intent, int alarmId, long timeInMiliseconds) {
        intent.putExtra("alarmId", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            am.setExactAndAllowWhileIdle(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            am.setExact(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        else
            am.set(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        saveAlarm(context, alarmId, timeInMiliseconds);
    }

    public static void cancelAlarm(Context context, Intent intent, int notificationId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        removeAlarmId(context, notificationId);
    }

    public static void cancelAllAlarms(Context context, Intent intent) {
        for (String idAlarm : getAlarms(context).keySet()) {
            cancelAlarm(context, intent, Integer.valueOf(idAlarm));
        }
    }

    public static void setAllAlarms(Context context, Intent alarmIntent) {
        Map<String, String> alarms = getAlarms(context);
        for (Map.Entry<String, String> alarm : alarms.entrySet()) {
            addAlarm(context, alarmIntent, Integer.valueOf(alarm.getKey()), Long.valueOf(alarm.getValue()));
        }
    }

    public static boolean hasAlarm(Context context, Intent intent, int notificationId) {
        return PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private static void saveAlarm(Context context, int id, long timeInMiliseconds) {
        Map<String, String> alarms = getAlarms(context);
        if (alarms.keySet().contains(String.valueOf(id)))
            return;
        alarms.put(String.valueOf(id), String.valueOf(timeInMiliseconds));
        saveIdsInPreferences(context, alarms);
    }

    private static void removeAlarmId(Context context, int id) {
        Map<String, String> alarms = getAlarms(context);
        for (String alarmId : alarms.keySet())
            if (Integer.valueOf(alarmId) == id)
                alarms.remove(alarmId);
        saveIdsInPreferences(context, alarms);
    }

    public static Map<String, String> getAlarms(Context context) {
        Map<String, String> alarms = new HashMap<>();
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            JSONObject alarmsJson = new JSONObject(prefs.getString(context.getPackageName() + ALARMS_TAG, "[]"));

            Iterator<String> alarmsIds = alarmsJson.keys();
            while (alarmsIds.hasNext()) {
                String alarmId = alarmsIds.next();
                alarms.put(alarmId, alarmsJson.getString(alarmId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarms;
    }

    private static void saveIdsInPreferences(Context context, Map<String, String> alarms) {
        JSONObject alarmsJson = new JSONObject();
        for (Map.Entry<String, String> alarm : alarms.entrySet()) {
            alarms.put(alarm.getKey(), alarm.getValue());
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getPackageName() + ALARMS_TAG, alarmsJson.toString());
        editor.apply();
    }
}