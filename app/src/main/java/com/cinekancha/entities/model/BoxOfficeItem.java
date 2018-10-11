package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BoxOfficeItem extends RealmObject {
    @PrimaryKey
    @SerializedName("movie_id")
    @Expose
    private String movieId;
    @SerializedName("movie_name")
    @Expose
    private String movieName;
    @SerializedName("movie_image")
    @Expose
    private String movieImage;
    @SerializedName("total_collected")
    @Expose
    private Integer totalCollected;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public Integer getTotalCollected() {
        return totalCollected;
    }

    public void setTotalCollected(Integer totalCollected) {
        this.totalCollected = totalCollected;
    }
}
