package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.R;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities.Record;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLogEntryActivity extends AppCompatActivity {
    private EditText date;
    private EditText time;
    private EditText glucose;
    private EditText fastInsuline;
    private EditText slowInsuline;
    private Spinner categoriesSpinner;
    private EditText note;

    private String selectedCategory = "";


    class SpinnerSelection implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCategory = ((CharSequence)adapterView.getSelectedItem()).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saving data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if (validateData()) {
                    saveDataToDatabase();
                    transitToLogBook();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddLogEntryActivity.this);
                    builder.setMessage(R.string.add_log_error_message)
                            .setTitle(R.string.add_log_error_message)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        initializeCategoriesSpinner();
        initializeWidgets();

        setOnFocusListenersForDateAndTime();

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(AddLogEntryActivity.this, HomeScreenActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    private boolean validateData() {
        boolean areValid = true;
        if (!isNumeric(this.glucose.getText().toString()) && this.glucose.getText().toString().length() > 0){
            areValid = false;
        }
        if (this.glucose.toString().isEmpty()){
            areValid = false;
        }

        if (!isNumeric(this.slowInsuline.getText().toString()) && this.slowInsuline.getText().toString().length() > 0){
            areValid = false;
        }

        if (!isNumeric(this.fastInsuline.getText().toString()) && this.fastInsuline.getText().toString().length() > 0){
            areValid = false;
        }

        if (this.date.getText().toString().isEmpty()){
            areValid = false;
        }

        if (this.time.getText().toString().isEmpty()){
            areValid = false;
        }

        return areValid;
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private void transitToLogBook() {
        startActivity(new Intent(AddLogEntryActivity.this, EntriesListActivity.class));

    }

    private void initializeCategoriesSpinner() {
        categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);
        categoriesSpinner.setSelection(0);
        categoriesSpinner.setOnItemSelectedListener(new SpinnerSelection());
        ArrayAdapter<CharSequence> categoriesSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoriesSpinner.setAdapter(categoriesSpinnerAdapter);
    }

    private void saveDataToDatabase() {
        Record record = new Record();
        record.setDate(date.getText().toString());
        record.setTime(time.getText().toString());
        record.setCategory(selectedCategory);
        record.setFastInsuline(fastInsuline.getText().toString());
        record.setSlowInsuline(slowInsuline.getText().toString());
        record.setNote(note.getText().toString());
        record.setGlucoseValue(glucose.getText().toString());

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUserId  = mFirebaseUser.getUid();

        DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth authentication = FirebaseAuth.getInstance();
        FirebaseUser user = authentication.getCurrentUser();
        firebaseDatabaseReference.child("users").child(mUserId).child("items").push().setValue(record);


    }

    private void initializeWidgets() {
        this.date = (EditText) findViewById(R.id.date_text_field);
        this.time = (EditText) findViewById(R.id.time_text_field);
        this.glucose = (EditText) findViewById(R.id.glucose_text_field);
        this.fastInsuline = (EditText) findViewById(R.id.fast_insuline_text_field);
        this.slowInsuline = (EditText) findViewById(R.id.slow_insuline_text_field);
        this.note = (EditText) findViewById(R.id.note_text_field);
    }

    private void setOnFocusListenersForDateAndTime() {
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDatePicker();
            }
        });

        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showTimePicker();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date.setText(datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear() );
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

    @TargetApi(Build.VERSION_CODES.N)
    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String hoursRaw = String.valueOf(i);
                String minutesRaw = String.valueOf(i1);
                if (hoursRaw.length() == 1)
                    hoursRaw = "0" + hoursRaw;
                if (minutesRaw.length() == 1)
                    minutesRaw = "0" + minutesRaw;
                time.setText(hoursRaw + ":" + minutesRaw);
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
