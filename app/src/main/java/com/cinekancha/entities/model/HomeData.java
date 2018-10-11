package com.cinekancha.entities.model;

import com.cinekancha.entities.GalleryItem;
import com.cinekancha.entities.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aayushsubedi on 3/8/18.
 */


public class HomeData extends RealmObject {
    @PrimaryKey
    private int id;
    @SerializedName("featuredContents")
    @Expose
    private RealmList<FeaturedContent> featuredContents = null;
    @SerializedName("new_releases")
    @Expose
    private RealmList<Movie> newReleases = null;
    @SerializedName("movies")
    @Expose
    private RealmList<Movie> movies = null;
    @SerializedName("box_office_RealmList")
    @Expose
    private RealmList<BoxOfficeItem> boxOfficeRealmList = null;
    @SerializedName("hot_news")
    @Expose
    private RealmList<Article> hotNews = null;
    @SerializedName("full_movies")
    @Expose
    private RealmList<Video> fullMovies = null;
    @SerializedName("trending_videos")
    @Expose
    private RealmList<Video> trendingVideo = null;
    @SerializedName("poll")
    @Expose
    private PollData poll;
    @SerializedName("trivia")
    @Expose
    private TriviaData triviaData;
    @SerializedName("troll")
    @Expose
    private TrollData troll;
    @SerializedName("top_story")
    @Expose
    private Article topStory;
    @SerializedName("photo_gallery")
    @Expose
    private RealmList<GalleryItem> photoGallery = new RealmList<>();
    @Expose
    @SerializedName("movie_reviews")
    private RealmList<Movie> featuredReviews = new RealmList<>();

    public RealmList<FeaturedContent> getFeaturedContents() {
        return featuredContents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeaturedContents(RealmList<FeaturedContent> featuredContents) {
        this.featuredContents = featuredContents;
    }

    public RealmList<Movie> getNewReleases() {
        return newReleases;
    }

    public void setNewReleases(RealmList<Movie> newReleases) {
        this.newReleases = newReleases;
    }

    public RealmList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(RealmList<Movie> movies) {
        this.movies = movies;
    }

    public RealmList<BoxOfficeItem> getBoxOfficeList() {
        return boxOfficeRealmList;
    }

    public void setBoxOfficeList(RealmList<BoxOfficeItem> boxOfficeRealmList) {
        this.boxOfficeRealmList = boxOfficeRealmList;
    }

    public RealmList<Article> getHotNews() {
        return hotNews;
    }

    public void setHotNews(RealmList<Article> hotNews) {
        this.hotNews = hotNews;
    }

    public RealmList<Video> getFullMovies() {
        return fullMovies;
    }

    public void setFullMovies(RealmList<Video> fullMovies) {
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

    public RealmList<GalleryItem> getPhotoGallery() {
        return photoGallery;
    }

    public void setPhotoGallery(RealmList<GalleryItem> photoGallery) {
        this.photoGallery = photoGallery;
    }

    public RealmList<Movie> getFeaturedReviews() {
        return featuredReviews;
    }

    public void setFeaturedReviews(RealmList<Movie> featuredReviews) {
        this.featuredReviews = featuredReviews;
    }

    public RealmList<Video> getTrendingVideo() {
        return trendingVideo;
    }

    public void setTrendingVideo(RealmList<Video> trendingVideo) {
        this.trendingVideo = trendingVideo;
    }
}