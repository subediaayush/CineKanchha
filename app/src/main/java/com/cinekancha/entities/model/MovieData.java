package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.annotations.PrimaryKey;

public class MovieData implements Serializable {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private List<Movie> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
