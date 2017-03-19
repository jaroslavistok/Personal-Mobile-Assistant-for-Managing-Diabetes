package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.data_entities;

public class Reminder {

    private String id;
    private String name;
    private String category;
    private String alarmTime;
    private boolean isActive;

    public Reminder() {
        this.id = "";
        this.name = "";
        this.category = "";
        this.alarmTime = "";
        isActive = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
