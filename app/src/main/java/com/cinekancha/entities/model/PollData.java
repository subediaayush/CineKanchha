package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.room.PrimaryKey;


public class PollData implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("total_votes")
    @Expose
    private Integer totalVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }
}