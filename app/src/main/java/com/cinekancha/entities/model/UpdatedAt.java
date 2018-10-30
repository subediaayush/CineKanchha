package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UpdatedAt implements Serializable {
    @PrimaryKey
    private int id = 0;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timezone_type")
    @Expose
    private Integer timezoneType;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(Integer timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}