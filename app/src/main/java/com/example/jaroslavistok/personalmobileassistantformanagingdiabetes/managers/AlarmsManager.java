package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AlarmsManager {

    private static final String ALARMS_TAG = ":alarms";
    public static int lastAlarmId = 0;
    public static String currentUserId = "";

    public static void addAlarm(Context context, Intent intent, int alarmId, long timeInMiliseconds) {
        intent.putExtra("alarmId", alarmId);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            am.setExactAndAllowWhileIdle(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            am.setExact(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        else
            am.set(ALARM_TYPE, timeInMiliseconds, pendingIntent);
        saveAlarmId(context, alarmId);
    }

    public static void cancelAlarm(Context context, Intent intent, int notificationId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        removeAlarmId(context, notificationId);
    }

    public static void cancelAllAlarms(Context context, Intent intent) {
        for (int idAlarm : getAlarmIds(context)) {
            Log.w("cancelling ", " " + idAlarm);
            cancelAlarm(context, intent, idAlarm);
        }
    }

    public static boolean hasAlarm(Context context, Intent intent, int notificationId) {
        return PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private static void saveAlarmId(Context context, int id) {
        List<Integer> idsAlarms = getAlarmIds(context);
        if (idsAlarms.contains(id))
            return;
        idsAlarms.add(id);
        saveIdsInPreferences(context, idsAlarms);
    }

    private static void removeAlarmId(Context context, int id) {
        List<Integer> idsAlarms = getAlarmIds(context);
        for (int i = 0; i < idsAlarms.size(); i++)
            if (idsAlarms.get(i) == id)
                idsAlarms.remove(i);
        saveIdsInPreferences(context, idsAlarms);
    }

    public static List<Integer> getAlarmIds(Context context) {
        List<Integer> ids = new ArrayList<>();
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            JSONArray jsonArray2 = new JSONArray(prefs.getString(context.getPackageName() + ALARMS_TAG, "[]"));
            for (int i = 0; i < jsonArray2.length(); i++)
                ids.add(jsonArray2.getInt(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    private static void saveIdsInPreferences(Context context, List<Integer> ids) {
        JSONArray jsonArray = new JSONArray();
        for (Integer idAlarm : ids)
            jsonArray.put(idAlarm);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getPackageName() + ALARMS_TAG, jsonArray.toString());
        editor.apply();
    }
}