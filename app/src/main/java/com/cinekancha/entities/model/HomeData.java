
package com.cinekancha.entities.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */


public class HomeData {

    @SerializedName("featuredContents")
    @Expose
    private List<FeaturedContent> featuredContents = null;
    @SerializedName("new_releases")
    @Expose
    private List<Movie> newReleases = null;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;
    @SerializedName("box_office_list")
    @Expose
    private List<BoxOfficeList> boxOfficeList = null;
    @SerializedName("hot_news")
    @Expose
    private List<Object> hotNews = null;
    @SerializedName("full_movies")
    @Expose
    private List<Movie> fullMovies = null;
    @SerializedName("poll")
    @Expose
    private PollData poll;
    @SerializedName("trivia")
    @Expose
    private Trivia trivia;
    @SerializedName("troll")
    @Expose
    private TrollData troll;
    @SerializedName("top_story")
    @Expose
    private TopStory topStory;

    public List<FeaturedContent> getFeaturedContents() {
        return featuredContents;
    }

    public void setFeaturedContents(List<FeaturedContent> featuredContents) {
        this.featuredContents = featuredContents;
    }

    public List<Movie> getNewReleases() {
        return newReleases;
    }

    public void setNewReleases(List<Movie> newReleases) {
        this.newReleases = newReleases;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<BoxOfficeList> getBoxOfficeList() {
        return boxOfficeList;
    }

    public void setBoxOfficeList(List<BoxOfficeList> boxOfficeList) {
        this.boxOfficeList = boxOfficeList;
    }

    public List<Object> getHotNews() {
        return hotNews;
    }

    public void setHotNews(List<Object> hotNews) {
        this.hotNews = hotNews;
    }

    public List<Movie> getFullMovies() {
        return fullMovies;
    }

    public void setFullMovies(List<Movie> fullMovies) {
        this.fullMovies = fullMovies;
    }

    public PollData getPoll() {
        return poll;
    }

    public void setPoll(PollData poll) {
        this.poll = poll;
    }

    public Trivia getTrivia() {
        return trivia;
    }

    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
    }

    public TrollData getTroll() {
        return troll;
    }

    public void setTroll(TrollData troll) {
        this.troll = troll;
    }

    public TopStory getTopStory() {
        return topStory;
    }

    public void setTopStory(TopStory topStory) {
        this.topStory = topStory;
    }

}