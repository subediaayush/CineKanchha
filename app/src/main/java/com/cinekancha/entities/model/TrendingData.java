package com.cinekancha.entities.model;

import java.util.List;
import com.cinekancha.entities.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingData {

    @SerializedName("data")
    @Expose
    private List<Video> trendingList = null;
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