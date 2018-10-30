package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class GalleryItem implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("image_url")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
