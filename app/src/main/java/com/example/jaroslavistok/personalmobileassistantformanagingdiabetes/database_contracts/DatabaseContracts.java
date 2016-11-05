package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts;


import android.provider.BaseColumns;

public interface DatabaseContracts {
    interface Entry extends BaseColumns {
        String TABLE_NAME = "entries";
        String TIMESTAMP = "timestamp";
        String GLUCOSE = "glucose";
    }
}
