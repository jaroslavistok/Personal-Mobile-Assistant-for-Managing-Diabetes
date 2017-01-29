package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.retrofit.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class PojoExample
{
    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private String id;

    @Override
    public String toString(){
        return login + " " + id;
    }

}
