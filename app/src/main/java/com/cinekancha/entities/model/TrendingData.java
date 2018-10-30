package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.realm.annotations.PrimaryKey;

public class TrendingData implements Serializable {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private List<Video> trendingList = new ArrayList<>();
    @SerializedName("links")
    @Expose
    private PageLink links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

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

    public List<Video> getTrendingList() {
        return trendingList;
    }

    public void setTrendingList(List<Video> trendingList) {
        this.trendingList = trendingList;
    }
}