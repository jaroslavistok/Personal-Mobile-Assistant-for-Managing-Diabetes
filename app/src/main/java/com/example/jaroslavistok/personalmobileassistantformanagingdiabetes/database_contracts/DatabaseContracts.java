package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts;


import android.provider.BaseColumns;

public interface DatabaseContracts {
    interface Entry extends BaseColumns {
        String TABLE_NAME = "entries";

        String DATE = "date";
        String TIME = "time";
        String GLUCOSE_VALUE = "glucose_value";
        String CATEGORY = "category";
        String FAST_INSULIN = "fast_insulin";
        String SLOW_INSULIN = "slow_insulin";
        String NOTE = "note";
        String SYNCHORNIZED = "synchronized";

    }
}
