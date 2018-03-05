package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

public class Crew {
    @SerializedName("story_screenplay_dialogues")
    private String story_screenplay_dialogues;
    @SerializedName("cinematographer")
    private String cinematographer;
    @SerializedName("choreographer")
    private String choreographer;
    @SerializedName("produced_by")
    private String produced_by;
    @SerializedName("directed_by")
    private String directed_by;
    @SerializedName("written_by")
    private String written_by;
    @SerializedName("edited_by")
    private String edited_by;
    @SerializedName("lyrics")
    private String lyrics;
    @SerializedName("music")
    private String music;
    @SerializedName("vfx")
    private String vfx;

    public String getStory_screenplay_dialogues() {
        return story_screenplay_dialogues;
    }

    public void setStory_screenplay_dialogues(String story_screenplay_dialogues) {
        this.story_screenplay_dialogues = story_screenplay_dialogues;
    }

    public String getCinematographer() {
        return cinematographer;
    }

    public void setCinematographer(String cinematographer) {
        this.cinematographer = cinematographer;
    }

    public String getChoreographer() {
        return choreographer;
    }

    public void setChoreographer(String choreographer) {
        this.choreographer = choreographer;
    }

    public String getProduced_by() {
        return produced_by;
    }

    public void setProduced_by(String produced_by) {
        this.produced_by = produced_by;
    }

    public String getDirected_by() {
        return directed_by;
    }

    public void setDirected_by(String directed_by) {
        this.directed_by = directed_by;
    }

    public String getWritten_by() {
        return written_by;
    }

    public void setWritten_by(String written_by) {
        this.written_by = written_by;
    }

    public String getEdited_by() {
        return edited_by;
    }

    public void setEdited_by(String edited_by) {
        this.edited_by = edited_by;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getVfx() {
        return vfx;
    }

    public void setVfx(String vfx) {
        this.vfx = vfx;
    }
}
