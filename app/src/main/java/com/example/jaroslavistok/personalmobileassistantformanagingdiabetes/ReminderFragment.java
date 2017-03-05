package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.utils.DefaultsConstantsValues.DISMISS_ACTION;


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

                    }
                })
                .setNegativeButton("Cancel", DISMISS_ACTION);

        return alert.create();
    }

}
