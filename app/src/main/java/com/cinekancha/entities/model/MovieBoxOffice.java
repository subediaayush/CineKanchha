package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.room.PrimaryKey;


public class MovieBoxOffice implements Serializable {
    
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    
    @SerializedName("movie_id")
    @Expose
    private String movieId;
    
    @SerializedName("movie_name")
    @Expose
    private String movieName;
    
    @SerializedName("movie_image")
    @Expose
    private String movieImage;
    
    @SerializedName("openingDay")
    @Expose
    private float openingDay;
    
    @SerializedName("openingWeekend")
    @Expose
    private float openingWeekend;
    
    @SerializedName("domestic")
    @Expose
    private float domestic;
    
    @SerializedName("international")
    @Expose
    private float international;
    
    @SerializedName("verdict")
    @Expose
    private String verdict;
    
    @SerializedName("day")
    @Expose
    private List<Float> day = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Float getOpeningDay() {
        return openingDay;
    }
    
    public void setOpeningDay(Float openingDay) {
        this.openingDay = openingDay;
    }
    
    public Float getOpeningWeekend() {
        return openingWeekend;
    }
    
    public void setOpeningWeekend(Float openingWeekend) {
        this.openingWeekend = openingWeekend;
    }
    
    public Float getDomestic() {
        return domestic;
    }
    
    public void setDomestic(Float domestic) {
        this.domestic = domestic;
    }
    
    public Float getInternational() {
        return international;
    }
    
    public void setInternational(Float international) {
        this.international = international;
    }
    
    public String getVerdict() {
        return verdict;
    }
    
    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }
    
    public List<Float> getDay() {
        return day;
    }
    
    public void setDay(List<Float> day) {
        this.day = day;
    }
}
