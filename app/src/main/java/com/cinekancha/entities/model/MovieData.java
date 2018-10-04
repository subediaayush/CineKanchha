package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieData {
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

}
