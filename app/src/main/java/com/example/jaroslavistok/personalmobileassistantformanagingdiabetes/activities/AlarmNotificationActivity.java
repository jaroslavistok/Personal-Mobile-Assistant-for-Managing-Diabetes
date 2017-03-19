package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.DiabetesApplication;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;

public class AlarmNotificationActivity extends AppCompatActivity {

    public static Intent currentRingingActivityIntent;

    public static Intent getCurrentRingingActivityIntent(){
        return currentRingingActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentRingingActivityIntent = this.getIntent();

        setContentView(R.layout.activity_alarm_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent activityIntent = getIntent();
        Log.w("Turbonufka", String.valueOf(activityIntent.getIntExtra("meganufka", 1)));

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                r.stop();
                DiabetesApplication.getInstance().isRinging = false;
            }
        });


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

}
