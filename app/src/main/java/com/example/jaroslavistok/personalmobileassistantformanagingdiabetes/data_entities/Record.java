package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities;


public class Record {

    private String datetime;
    private String glucoseValue;
    private String fastInsuline;
    private String slowInsuline;
    private String category;
    private String carbs;
    private String note;

    public Record(){
        datetime = "";
        glucoseValue = "";
        fastInsuline = "";
        slowInsuline = "";
        category = "";
        carbs = "";
        note = "";
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
