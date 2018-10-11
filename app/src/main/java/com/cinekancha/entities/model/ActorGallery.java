package com.cinekancha.entities.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ActorGallery extends RealmObject {
    @PrimaryKey
    private int id = 0;
    @SerializedName("data")
    @Expose
    private RealmList<Actor> data = null;
    @SerializedName("links")
    @Expose
    private PageLink links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Actor> getData() {
        return data;
    }

    public void setData(RealmList<Actor> data) {
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