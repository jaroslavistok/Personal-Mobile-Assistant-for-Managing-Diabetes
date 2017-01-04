package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HomeScreen extends AppCompatActivity {

    private ImageButton addLogEntryButton;
    private ImageButton showDatabaseButton;
    private ImageButton showDataButton;
    private ImageButton showStatisticsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        addLogEntryButton =  (ImageButton) findViewById(R.id.addButton);
        showDatabaseButton = (ImageButton) findViewById(R.id.showDatabaseButton);
        showDataButton = (ImageButton) findViewById(R.id.showDataButton);
        showStatisticsButton = (ImageButton) findViewById(R.id.statisticsButton);


        addLogEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, MainActivity.class));
            }
        });


    }
}
