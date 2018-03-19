package com.cinekancha.entities.model;

import com.cinekancha.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeData {
	private List<FeaturedItem> featuredItems = new ArrayList<>();
	private List<Movie> newReleases = new ArrayList<>();
	private List<Movie> upcomingMovies = new ArrayList<>();
	private Article featuredArticle;
	private List<Article> topArticles = new ArrayList<>();
	private Poll featuredPoll;
	private Trivia featuredTrivia;
	private List<Troll> featuredTrolls;
	private List<Movie> featuredMovies = new ArrayList<>();
	
	public int getItemCount(){
		int count = 0;
		if (!ListUtils.isEmpty(featuredItems)) count += 1;
		if (!ListUtils.isEmpty(newReleases)) count += 1;
		if (!ListUtils.isEmpty(upcomingMovies)) count += 1;
		if (featuredArticle != null) count += 1;
		if (!ListUtils.isEmpty(topArticles)) count += 1;
		if (featuredPoll != null) count += 1;
		if (featuredTrivia != null) count += 1;
		if (!ListUtils.isEmpty(featuredTrolls)) count += 1;
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
	
	public Poll getFeaturedPoll() {
		return featuredPoll;
	}
	
	public Trivia getFeaturedTrivia() {
		return featuredTrivia;
	}
	
	public List<Troll> getFeaturedTrolls() {
		return featuredTrolls;
	}
	
	public List<Movie> getFeaturedMovies() {
		return featuredMovies;
	}
}
