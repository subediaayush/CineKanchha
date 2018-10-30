package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Crew implements Serializable {
    @PrimaryKey
    private int id;
    @SerializedName("directed_by")
    @Expose
    private String directedBy;
    @SerializedName("produced_by")
    @Expose
    private String producedBy;
    @SerializedName("written_by")
    @Expose
    private String writtenBy;
    @SerializedName("edited_by")
    @Expose
    private String editedBy;
    @SerializedName("story_screenplay_dialogues")
    @Expose
    private String storyScreenplayDialogues;
    @SerializedName("cinematographer")
    @Expose
    private String cinematographer;
    @SerializedName("vfx")
    @Expose
    private String vfx;
    @SerializedName("music")
    @Expose
    private String music;
    @SerializedName("lyrics")
    @Expose
    private String lyrics;
    @SerializedName("choreographer")
    @Expose
    private String choreographer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public String getProducedBy() {
        return producedBy;
    }

    public void setProducedBy(String producedBy) {
        this.producedBy = producedBy;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getStoryScreenplayDialogues() {
        return storyScreenplayDialogues;
    }

    public void setStoryScreenplayDialogues(String storyScreenplayDialogues) {
        this.storyScreenplayDialogues = storyScreenplayDialogues;
    }

    public String getCinematographer() {
        return cinematographer;
    }

    public void setCinematographer(String cinematographer) {
        this.cinematographer = cinematographer;
    }

    public String getVfx() {
        return vfx;
    }

    public void setVfx(String vfx) {
        this.vfx = vfx;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getChoreographer() {
        return choreographer;
    }

    public void setChoreographer(String choreographer) {
        this.choreographer = choreographer;
    }

}