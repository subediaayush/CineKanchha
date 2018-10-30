package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.annotations.PrimaryKey;

public class Troll implements Serializable {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private List<TrollData> data = null;
    @SerializedName("links")
    @Expose
    private PageLink links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<TrollData> getData() {
        return data;
    }

    public void setData(List<TrollData> data) {
        this.data = data;
    }

    public PageLink getLinks() {
        return links;
    }

    public void setLinks(PageLink links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}