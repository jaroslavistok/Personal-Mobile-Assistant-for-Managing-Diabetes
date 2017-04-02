package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities;

import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.DiabetesApplication;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers.BootReceiver;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.receivers.SampleAlarmReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class HomeScreenActivity extends AppCompatActivity {

    public static boolean alreadyCalled = false;
    private Context context;
    private String uid;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                mFirebaseUser = user;
                Log.d("nufi", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                mFirebaseUser = null;
                Log.d("nufi", "onAuthStateChanged:signed_out");
            }
        }
    };

    private DatabaseReference databaseReference;

    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(firebaseAuthListener);

        if (mFirebaseUser != null) {
            setUpListenerForSettingAlarms();
            setUpListenerForAlarmIds();
        }

        context = getApplicationContext();
        setUpAlarmsOnDeviceBoot();
    }

    private void setUpAlarmsOnDeviceBoot() {
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onStop(){
        super.onStop();
        auth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (DiabetesApplication.getInstance().isRinging){
            startActivity(AlarmNotificationActivity.getCurrentRingingActivityIntent());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.auth = FirebaseAuth.getInstance();

        if (DiabetesApplication.getInstance().isRinging){
            startActivity(AlarmNotificationActivity.getCurrentRingingActivityIntent());
        }

        if (!alreadyCalled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            alreadyCalled = true;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if (mFirebaseUser == null) {
            loadLogInView();
        } else {

            AlarmsManager.currentUserId = mFirebaseUser.getUid();

            ImageButton addLogEntryButton = (ImageButton) findViewById(R.id.addButton);
            ImageButton showDatabaseButton = (ImageButton) findViewById(R.id.showDatabaseButton);
            ImageButton showDataButton = (ImageButton) findViewById(R.id.showDataButton);
            ImageButton showStatisticsButton = (ImageButton) findViewById(R.id.statisticsButton);

            addLogEntryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeScreenActivity.this, AddLogEntryActivity.class));
                }
            });

            showDatabaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeScreenActivity.this, EntriesListActivity.class));
                }

            });

            showDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeScreenActivity.this, RemindersActivity.class));
                }

            });

            showStatisticsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeScreenActivity.this, GraphViewExampleActivity.class));
                }
            });


            Button logoutButton = (Button)findViewById(R.id.logout_button);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    auth.signOut();
                    loadLogInView();
                }
            });


        }
    }

    private void setUpListenerForAlarmIds() {

        databaseReference.child("users").child(AlarmsManager.currentUserId).child("last_reminder_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    AlarmsManager.lastAlarmId = Integer.valueOf(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpListenerForSettingAlarms() {


        databaseReference.child("users").child(AlarmsManager.currentUserId).child("reminders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Reminder reminder = dataSnapshot.getValue(Reminder.class);

                    String parsedTime[] = reminder.getAlarmTime().split(":");
                    int hour = Integer.valueOf(parsedTime[0]);
                    int minute = Integer.valueOf(parsedTime[1]);

                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
                    calendar.set(java.util.Calendar.MINUTE, minute);

                    long alarmMilis = 0;

                    if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                        alarmMilis = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
                    else
                        alarmMilis = calendar.getTimeInMillis();


                    if (reminder.isActive()) {
                        Intent intent = new Intent(HomeScreenActivity.this, SampleAlarmReceiver.class);
                        AlarmsManager.addAlarm(HomeScreenActivity.this, intent, Integer.valueOf(reminder.getId()), alarmMilis);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Reminder reminder = dataSnapshot.getValue(Reminder.class);

                    String parsedTime[] = reminder.getAlarmTime().split(":");
                    int hour = Integer.valueOf(parsedTime[0]);
                    int minute = Integer.valueOf(parsedTime[1]);

                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    Calendar now = Calendar.getInstance();

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
                    calendar.set(java.util.Calendar.MINUTE, minute);
                    long alarmMilis = 0;
                    if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                        alarmMilis = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
                    else
                        alarmMilis = calendar.getTimeInMillis();

                    Intent intent = new Intent(HomeScreenActivity.this, SampleAlarmReceiver.class);
                    if (reminder.isActive()) {
                        AlarmsManager.cancelAlarm(HomeScreenActivity.this, intent, Integer.valueOf(reminder.getId()));
                        AlarmsManager.addAlarm(HomeScreenActivity.this, intent, Integer.valueOf(reminder.getId()), alarmMilis);
                    } else {
                        AlarmsManager.cancelAlarm(HomeScreenActivity.this, intent, Integer.valueOf(reminder.getId()));
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent intent = new Intent(HomeScreenActivity.this, SampleAlarmReceiver.class);
                    Reminder reminder = dataSnapshot.getValue(Reminder.class);
                    if (reminder.isActive()) {
                        AlarmsManager.cancelAlarm(HomeScreenActivity.this, intent, Integer.valueOf(reminder.getId()));
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
