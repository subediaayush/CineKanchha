package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieData extends RealmObject {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private RealmList<Movie> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public RealmList<Movie> getData() {
        return data;
    }

    public void setData(RealmList<Movie> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
