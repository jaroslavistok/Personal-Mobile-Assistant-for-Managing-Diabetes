package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ReminderFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_reminder_dialog, null);

        alert.setTitle("Add a new note").setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameEditText = (EditText) view.findViewById(R.id.reminder_name_input);
                        EditText categoryEditText = (EditText) view.findViewById(R.id.reminder_category_input);
                        EditText alarmTimeEditText = (EditText) view.findViewById(R.id.datetime_reminder_pick);

                        Reminder record = new Reminder();
                        record.setName(String.valueOf(nameEditText.getText()));
                        record.setCategory(String.valueOf(categoryEditText.getText()));
                        record.setAlarmTime(String.valueOf(alarmTimeEditText.getText()));
                        record.setActive(false);

                        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        String mUserId  = null;
                        if (mFirebaseUser != null)
                            mUserId = mFirebaseUser.getUid();
                        else
                            mUserId = "";


                        DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth authentication = FirebaseAuth.getInstance();
                        FirebaseUser user = authentication.getCurrentUser();
                        firebaseDatabaseReference.child("users").child(mUserId).child("reminders").push().setValue(record);
                    }
                })
                .setNegativeButton("Cancel", null);

        return alert.create();
    }

}
