package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.services;

import android.util.Log;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers.SampleAlarmReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class SyncingFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ..
        Log.w("received", "on message received");
        String logType = "w";

//        SampleAlarmReceiver alarm = new SampleAlarmReceiver();
//        alarm.setAlarm(this);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(logType, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(logType, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(logType, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


}
