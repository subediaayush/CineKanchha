package com.cinekancha.entities.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoxOffice {

    @SerializedName("data")
    @Expose
    private List<BoxOfficeItem> boxOfficeItems = null;
    @SerializedName("links")
    @Expose
    private PageLink links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<BoxOfficeItem> getData() {
        return boxOfficeItems;
    }

    public void setData(List<BoxOfficeItem> data) {
        this.boxOfficeItems = data;
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