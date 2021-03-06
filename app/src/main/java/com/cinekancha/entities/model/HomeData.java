package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.room.PrimaryKey;


/**
 * Created by aayushsubedi on 3/8/18.
 */


public class HomeData implements Serializable {
    @PrimaryKey
    private long id;
    @SerializedName("featuredContents")
    @Expose
    private List<FeaturedContent> featuredContents = null;
    @SerializedName("new_releases")
    @Expose
    private List<Movie> newReleases = null;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;
    @SerializedName("box_offices")
    @Expose
    private List<MovieBoxOffice> boxOfficeList = null;
    @SerializedName("hot_news")
    @Expose
    private List<Article> hotNews = null;
    @SerializedName("full_movies")
    @Expose
    private List<Video> fullMovies = null;
    @SerializedName("trending_videos")
    @Expose
    private List<Video> trendingVideo = null;
    @SerializedName("poll")
    @Expose
    private PollData poll;
    @SerializedName("trivia")
    @Expose
    private TriviaData triviaData;
    @SerializedName("troll")
    @Expose
    private TrollData troll;
    @SerializedName("upcoming_movies")
    @Expose
    private List<Movie> upComingMovies = null;
    @SerializedName("top_story")
    @Expose
    private Article topStory;
    @SerializedName("top_stories")
    @Expose
    private List<Article> topStories;
    @SerializedName("photo_gallery")
    @Expose
    private List<GalleryItem> photoGallery = new ArrayList<>();
    @Expose
    @SerializedName("movie_reviews")
    private List<Movie> featuredReviews = new ArrayList<>();

    public List<FeaturedContent> getFeaturedContents() {
        return featuredContents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<MovieBoxOffice> getBoxOfficeList() {
        return boxOfficeList;
    }

    public void setBoxOfficeList(List<MovieBoxOffice> boxOfficeList) {
        this.boxOfficeList = boxOfficeList;
    }

    public List<Article> getHotNews() {
        return hotNews;
    }

    public void setHotNews(List<Article> hotNews) {
        this.hotNews = hotNews;
    }

    public List<Video> getFullMovies() {
        return fullMovies;
    }

    public void setFullMovies(List<Video> fullMovies) {
        this.fullMovies = fullMovies;
    }

    public PollData getPoll() {
        return poll;
    }

    public void setPoll(PollData poll) {
        this.poll = poll;
    }

    public TriviaData getTriviaData() {
        return triviaData;
    }

    public void setTriviaData(TriviaData triviaData) {
        this.triviaData = triviaData;
    }

    public TrollData getTroll() {
        return troll;
    }

    public void setTroll(TrollData troll) {
        this.troll = troll;
    }

    public Article getTopStory() {
        return topStory;
    }

    public void setTopStory(Article topStory) {
        this.topStory = topStory;
    }

    public List<GalleryItem> getPhotoGallery() {
        return photoGallery;
    }

    public void setPhotoGallery(List<GalleryItem> photoGallery) {
        this.photoGallery = photoGallery;
    }

    public List<Movie> getFeaturedReviews() {
        return featuredReviews;
    }

    public void setFeaturedReviews(List<Movie> featuredReviews) {
        this.featuredReviews = featuredReviews;
    }

    public List<Video> getTrendingVideo() {
        return trendingVideo;
    }

    public void setTrendingVideo(List<Video> trendingVideo) {
        this.trendingVideo = trendingVideo;
    }
    
    public List<Movie> getUpComingMovies() {
        return upComingMovies;
    }
    
    public void setUpComingMovies(List<Movie> upComingMovies) {
        this.upComingMovies = upComingMovies;
    }

    public List<Article> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<Article> topStories) {
        this.topStories = topStories;
    }
}