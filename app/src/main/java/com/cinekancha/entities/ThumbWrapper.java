package com.cinekancha.entities;

import androidx.annotation.DrawableRes;

public class ThumbWrapper {
    private String imageUrl;
    @DrawableRes
    private int imageRes = -1;
    private String caption;
    private String subCaption;
    private long id;

    public ThumbWrapper(String imageUrl, String caption, String subCaption, long id) {
        this(imageUrl, -1, caption, subCaption, id);
    }

    public ThumbWrapper(String imageUrl, String caption, long id) {
        this(imageUrl, -1, caption, "", id);
    }

    public ThumbWrapper(@DrawableRes int imageRes, String caption, String subCaption) {
        this("", imageRes, caption, subCaption, -1);
    }

    public ThumbWrapper(@DrawableRes int imageRes, String caption) {
        this("", imageRes, caption, "", -1);
    }

    private ThumbWrapper(String imageUrl, int imageRes, String caption, String subCaption, long id) {
        this.imageUrl = imageUrl;
        this.imageRes = imageRes;
        this.caption = caption;
        this.subCaption = subCaption;
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getCaption() {
        return caption;
    }

    public String getSubCaption() {
        return subCaption;
    }

    public long getId() {
        return id;
    }
}
