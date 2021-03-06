package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.PrimaryKey;


public class Option implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("vote")
    @Expose
    private Integer vote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

}