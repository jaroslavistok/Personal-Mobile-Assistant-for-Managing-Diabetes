package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes;

import android.app.Application;

public class DiabetesApplication extends Application {

    private static DiabetesApplication instance;

    public boolean isRinging = false;

    public static DiabetesApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }


}
