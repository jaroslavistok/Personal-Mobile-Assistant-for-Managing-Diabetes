package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Record;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.adapters.RecordsAdapter;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.fragments.FilterFragment;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.fragments.ReminderFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EntriesListActivity extends AppCompatActivity {


    private String mUserId;
    public ArrayAdapter<Record> recordArrayAdapter;
    private List<Record> items = new ArrayList<Record>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mUserId = mFirebaseUser.getUid();

        // Set up ListView
        final ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<Record> recordList = new ArrayList<>();
        final RecordsAdapter recordsAdapter = new RecordsAdapter(this, recordList);
        recordsAdapter.setInitialItems(items);
        this.recordArrayAdapter = recordsAdapter;
        listView.setAdapter(recordsAdapter);


        final Button filterButton = (Button) findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilterFragment filterFragment = new FilterFragment();
                filterFragment.show(getSupportFragmentManager(), "tag");
            }
        });

        final FloatingActionButton addNewEntryButton = (FloatingActionButton) findViewById(R.id.fab_list);
        addNewEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntriesListActivity.this, AddLogEntryActivity.class);
                startActivity(intent);
            }
        });




        mDatabase.child("users").child(mUserId).child("items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                recordsAdapter.add(dataSnapshot.getValue(Record.class));
                items.add(dataSnapshot.getValue(Record.class));
                recordsAdapter.getItems();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                recordsAdapter.remove(dataSnapshot.getValue(Record.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(EntriesListActivity.this, HomeScreenActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            return true;
        }
        return false;
    }


}
