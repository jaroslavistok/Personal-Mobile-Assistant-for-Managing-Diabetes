package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit;

public class Entrydata {
    private String category;
    private String glucose;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String dateTime;

    public Entrydata(String glucose, String dateTime, String category){
        this.category = category;
        this.glucose = glucose;
        this.dateTime = dateTime;
    }

    public Entrydata(){
        this.glucose = "";
        this.dateTime = "";
        this.category = "";
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGlucose() {
        return glucose;
    }

    public String getDateTime() {
        return dateTime;
    }
}
