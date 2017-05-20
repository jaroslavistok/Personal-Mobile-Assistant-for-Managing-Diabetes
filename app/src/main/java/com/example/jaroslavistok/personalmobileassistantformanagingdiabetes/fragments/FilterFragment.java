package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities.EntriesListActivity;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Reminder;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.managers.AlarmsManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FilterFragment extends DialogFragment {

    private EditText fromDateEditText;
    private EditText toDateEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.filter_dialog, null);

        fromDateEditText = (EditText) view.findViewById(R.id.from_date);
        fromDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePicker("from");
                }
            }
        });

        toDateEditText = (EditText) view.findViewById(R.id.to_date);
        toDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePicker("to");
                }
            }
        });

        alert.setTitle("Pridaj nový filter").setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText fromDate = (EditText) view.findViewById(R.id.from_date);
                EditText toDate = (EditText) view.findViewById(R.id.to_date);
                String filterDateRange = "";
                filterDateRange += fromDate.getText().toString() + "&" + toDate.getText().toString();
                if (validateFilterData(fromDate, toDate)) {
                    ((EntriesListActivity)getActivity()).recordArrayAdapter.getFilter().filter(filterDateRange);
                } else {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setMessage(R.string.add_log_error_message)
                            .setTitle(R.string.add_log_error_message)
                            .setPositiveButton(android.R.string.ok, null);
                    android.support.v7.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }).setNegativeButton("Zrušiť", null);
        return alert.create();
    }

    private boolean validateFilterData(EditText fromDate, EditText toDate) {
        boolean isValid = true;
        if (fromDate.getText().toString().isEmpty()){
            isValid = false;
        }
        if (toDate.getText().toString().isEmpty()){
            isValid = false;
        }

        return isValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDatePicker(final String type) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (type.equals("to"))
                    toDateEditText.setText(datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear() );
                else if (type.equals("from"))
                    fromDateEditText.setText(datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear() );
            }
        }, year, month, dayOfMonth);

        toggleDatePicker(datePicker);
    }

    private void toggleDatePicker(DatePickerDialog datePicker) {
        if (datePicker.isShowing())
            datePicker.hide();
        else
            datePicker.show();
    }

}
