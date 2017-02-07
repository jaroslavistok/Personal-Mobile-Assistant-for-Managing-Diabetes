package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.alarm_example.AlarmActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_helpers.EntriesDatabaseHelper;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test.MainActivity;

public class HomeScreen extends AppCompatActivity {

    private ImageButton addLogEntryButton;
    private ImageButton showDatabaseButton;
    private ImageButton showDataButton;
    private ImageButton showStatisticsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        EntriesDatabaseHelper entriesDatabaseHelper = new EntriesDatabaseHelper(this);
        

        addLogEntryButton =  (ImageButton) findViewById(R.id.addButton);
        showDatabaseButton = (ImageButton) findViewById(R.id.showDatabaseButton);
        showDataButton = (ImageButton) findViewById(R.id.showDataButton);
        showStatisticsButton = (ImageButton) findViewById(R.id.statisticsButton);


        addLogEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HomeScreen.this, AddLogEntry.class));
            }
        });

        showDatabaseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HomeScreen.this, AlarmActivity.class));
            }

        });

        showDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HomeScreen.this, MainActivity.class));
            }

        });

        showStatisticsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(HomeScreen.this, AddLogEntry.class));
            }
        });
    }
}
