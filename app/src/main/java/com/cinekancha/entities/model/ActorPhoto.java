package com.cinekancha.entities.model;

import java.util.List;

import com.cinekancha.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ActorPhoto extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("photos")
    @Expose
    private RealmList<Photos> photos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RealmList<Photos> getPhotos() {
       /* for (int i = 0; i < photos.size(); i++) {
            String photo = photos.get(i);
            photos.set(i, Constants.imageUrl + photo);
        }*/
        return photos;
    }

    public void setPhotos(RealmList<Photos> photos) {
        this.photos = photos;
    }

}