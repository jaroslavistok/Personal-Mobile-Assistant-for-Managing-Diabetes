package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;

import java.util.List;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            AlarmsManager.setAllAlarms(context, intent);

        }
    }

}
