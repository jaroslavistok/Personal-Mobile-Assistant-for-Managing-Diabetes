package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            loadLogInView();
        } else {

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
                    startActivity(new Intent(HomeScreenActivity.this, AddLogEntryActivity.class));
                }
            });


            Button logoutButton = (Button)findViewById(R.id.logout_button);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFirebaseAuth.signOut();
                    loadLogInView();
                }
            });


        }
    }


    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
