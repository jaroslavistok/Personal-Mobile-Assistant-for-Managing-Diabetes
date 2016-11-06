package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;

public class EntryData {
    private String glucose;
    private String time;

    public EntryData(String glucose, String time){
        this.glucose = glucose;
        this.time = time;
    }

    public EntryData(){
        this.glucose = "";
        this.time = "";
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGlucose() {
        return glucose;
    }

    public String getTime() {
        return time;
    }
}
