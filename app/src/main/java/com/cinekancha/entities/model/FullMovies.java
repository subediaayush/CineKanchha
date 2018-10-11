package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FullMovies extends RealmObject {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private RealmList<Video> trendingList = null;
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

    public RealmList<Video> getTrendingList() {
        return trendingList;
    }

    public void setTrendingList(RealmList<Video> trendingList) {
        this.trendingList = trendingList;
    }
}