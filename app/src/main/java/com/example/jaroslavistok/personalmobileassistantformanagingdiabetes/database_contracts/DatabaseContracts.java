package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.database_contracts;


public interface DatabaseContracts {
    interface Entry {
        String TABLE_NAME = "entries";
        String TIMESTAMP = "timestamp";
        String GLUCOSE = "glucose";
    }

}
