package com.cinekancha.entities.model;

import com.cinekancha.entities.GalleryItem;
import com.cinekancha.entities.Video;
import com.cinekancha.utils.ListUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeData {
	@SerializedName("featuredContents")
	private List<FeaturedItem> featuredItems = new ArrayList<>();
	
	@SerializedName("new_releases")
	private List<Movie> newReleases = new ArrayList<>();
	
	@SerializedName("movies")
	private List<Movie> upcomingMovies = new ArrayList<>();
	
	private Article featuredArticle;
	
	@SerializedName("hot_news")
	private List<Article> topArticles = new ArrayList<>();
	
	@SerializedName("box_office_list")
	private List<BoxOfficeItem> boxOfficeItems = new ArrayList<>();
	
	@SerializedName("photo_gallery")
	private List<GalleryItem> photoGallery = new ArrayList<>();
	
	@SerializedName("movie_reviews")
	private List<Movie> featuredReviews = new ArrayList<>();
	
	@SerializedName("poll")
	private Poll featuredPoll;
	
	@SerializedName("trivia")
	private Trivia featuredTrivia;

	@SerializedName("troll")
	private Troll featuredTrolls;
	
	private List<Movie> featuredMovies = new ArrayList<>();
	
	private List<Movie> showTimes;
	
	@SerializedName("trending_videos")
	private List<Video> featuredTrending = new ArrayList<>();

	@SerializedName("full_movies")
	private List<Video> fullVideos = new ArrayList<>();
	
	public int getItemCount(){
		int count = 0;
		if (!ListUtils.isEmpty(featuredItems)) count += 1;
		if (!ListUtils.isEmpty(newReleases)) count += 1;
		if (!ListUtils.isEmpty(upcomingMovies)) count += 1;
		if (featuredArticle != null) count += 1;
		if (!ListUtils.isEmpty(topArticles)) count += 1;
		if (featuredPoll != null) count += 1;
		if (featuredTrivia != null) count += 1;
		if (featuredTrolls != null) count += 1;
		if (!ListUtils.isEmpty(featuredMovies)) count += featuredMovies.size();
		
		return count;
	}
	
	public List<FeaturedItem> getFeaturedItems() {
		return featuredItems;
	}
	
	public List<Movie> getNewReleases() {
		return newReleases;
	}
	
	public List<Movie> getUpcomingMovies() {
		return upcomingMovies;
	}
	
	public Article getFeaturedArticle() {
		return featuredArticle;
	}
	
	public List<Article> getTopArticles() {
		return topArticles;
	}
	
	public List<BoxOfficeItem> getBoxOfficeItems() {
		return boxOfficeItems;
	}
	
	public Poll getFeaturedPoll() {
		return featuredPoll;
	}
	
	public Trivia getFeaturedTrivia() {
		return featuredTrivia;
	}
	
	public Troll getFeaturedTrolls() {
		return featuredTrolls;
	}
	
	public List<Movie> getFeaturedMovies() {
		return featuredMovies;
	}
	
	public List<GalleryItem> getPhotoGallery() {
		return photoGallery;
	}
	
	public List<Movie> getFeaturedReviews() {
		return featuredReviews;
	}
	
	public List<Movie> getShowTimes() {
		return showTimes;
	}
	
	public List<Video> getFeaturedTrending() {
		return featuredTrending;
	}
	
	public List<Video> getFullVideos() {
		return fullVideos;
	}
}
