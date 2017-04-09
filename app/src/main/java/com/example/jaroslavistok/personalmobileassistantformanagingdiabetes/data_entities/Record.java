package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities;


public class Record {

    private String date;
    private String time;
    private String glucoseValue;
    private String fastInsuline;
    private String slowInsuline;
    private String category;
    private String carbs;
    private String note;

    public Record(){
        time = "";
        date = "";
        glucoseValue = "";
        fastInsuline = "";
        slowInsuline = "";
        category = "";
        note = "";
    }


    public String getGlucoseValue() {
        return glucoseValue;
    }

    public void setGlucoseValue(String glucoseValue) {
        this.glucoseValue = glucoseValue;
    }

    public String getFastInsuline() {
        return fastInsuline;
    }

    public void setFastInsuline(String fastInsuline) {
        this.fastInsuline = fastInsuline;
    }

    public String getSlowInsuline() {
        return slowInsuline;
    }

    public void setSlowInsuline(String slowInsuline) {
        this.slowInsuline = slowInsuline;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
