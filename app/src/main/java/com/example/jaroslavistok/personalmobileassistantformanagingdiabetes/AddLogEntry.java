package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test.EntriesProvider;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test.EntriesTableContract;
import com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test.MainActivity;

import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.LogBookActivity.INSERT_NOTE_TOKEN;
import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.NO_COOKIE;

public class AddLogEntry extends AppCompatActivity {
    private EditText date;
    private EditText time;
    private EditText glucose;
    private EditText fastInsuline;
    private EditText slowInsuline;
    private EditText note;

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

                saveDataToDatabase();
                finish();
                startActivity(new Intent(AddLogEntry.this, MainActivity.class));
            }
        });

        initializeWidgets();
        setOnFocusListenersForDateAndTime();
    }

    private void saveDataToDatabase() {
        String date = String.valueOf(this.date.getText());
        String time = String.valueOf(this.time.getText());
        String glucose = String.valueOf(this.glucose.getText());
        String fastInsuline = String.valueOf(this.fastInsuline.getText());
        String slowInsuline = String.valueOf(this.slowInsuline.getText());
        String note = String.valueOf(this.note.getText());

        Uri entriesContentProviderUri = EntriesProvider.urlForItems(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntriesTableContract.DATE, date);
        contentValues.put(EntriesTableContract.TIME, time);
        contentValues.put(EntriesTableContract.GLUCOSE_VALUE, glucose);
        contentValues.put(EntriesTableContract.FAST_INSULIN, fastInsuline);
        contentValues.put(EntriesTableContract.SLOW_INSULIN, slowInsuline);
        contentValues.put(EntriesTableContract.NOTE, note);
        contentValues.put(EntriesTableContract.CATEGORY, "empty");
        contentValues.put(EntriesTableContract.SYNCHORNIZED, 0);


        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(AddLogEntry.this, "Note was saved", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        insertHandler.startInsert(INSERT_NOTE_TOKEN, NO_COOKIE, entriesContentProviderUri, contentValues);
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
                Log.w("date", "ondate");
                date.setText(String.valueOf(i)+"."+String.valueOf(i1)+"."+String.valueOf(i2));
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
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.w("time", "ontime");
                time.setText(String.valueOf(i) + ":" + String.valueOf(i1));
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
