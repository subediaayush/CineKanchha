package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.PrimaryKey;


public class TriviaData implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("trivia")
    @Expose
    private String trivia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrivia() {
        return trivia;
    }

    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }

}