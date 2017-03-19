package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ReminderFragment extends DialogFragment {

    private EditText alarmTimeEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_reminder_dialog, null);

        alarmTimeEditText = (EditText) view.findViewById(R.id.datetime_reminder_pick);
        alarmTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showTimePicker();
                }
            }
        });

        alert.setTitle("Add a new note").setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameEditText = (EditText) view.findViewById(R.id.reminder_name_input);
                        EditText categoryEditText = (EditText) view.findViewById(R.id.reminder_category_input);
                        EditText alarmTimeEditText = (EditText) view.findViewById(R.id.datetime_reminder_pick);
                        String alarmId = String.valueOf(AlarmsManager.lastAlarmId +1);
                        Reminder record = new Reminder();
                        record.setName(String.valueOf(nameEditText.getText()));
                        record.setCategory(String.valueOf(categoryEditText.getText()));
                        record.setAlarmTime(String.valueOf(alarmTimeEditText.getText()));
                        record.setId(alarmId);
                        record.setActive(false);

                        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        String mUserId  = null;
                        if (mFirebaseUser != null)
                            mUserId = mFirebaseUser.getUid();
                        else
                            mUserId = "";
                        DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                        firebaseDatabaseReference.child("users").child(mUserId).child("reminders").push().setValue(record);
                        firebaseDatabaseReference.child("users").child(mUserId).child("last_reminder_id").setValue(alarmId);
                    }
                }).setNegativeButton("Cancel", null);
        return alert.create();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.w("time", "ontime");
                alarmTimeEditText.setText(String.valueOf(i) + ":" + String.valueOf(i1));
            }
        }, hour, minute, true);

        toggleTimePicker(timePickerDialog);
    }

    private void toggleTimePicker(TimePickerDialog timePickerDialog) {
        if (timePickerDialog.isShowing())
            timePickerDialog.hide();
        else
            timePickerDialog.show();
    }

}
