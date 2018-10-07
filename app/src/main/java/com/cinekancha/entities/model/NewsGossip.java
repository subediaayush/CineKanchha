package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsGossip {
    @SerializedName("data")
    @Expose
    private List<Article> data = null;
    @SerializedName("links")
    @Expose
    private PageLink links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
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
