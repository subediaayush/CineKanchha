package com.cinekancha.entities.model;

import com.cinekancha.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Photos implements Serializable {
    @PrimaryKey
    private int id = 0;
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return Constants.imageUrl + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}