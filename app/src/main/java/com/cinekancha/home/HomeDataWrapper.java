package com.cinekancha.home;

import android.util.SparseIntArray;

import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeDataWrapper {
	
	public static final int NEW_MOVIES = 1;
	public static final int UPCOMING_MOVIES = 2;
	public static final int FEATURED_ARTICLE_HIGHLIGHTED = 3;
	public static final int FEATURED_ARTICLE = 4;
	public static final int FEATURED_POLL = 5;
	public static final int FEATURED_TRIVIA = 6;
	public static final int FEATURED_TROLL = 7;
	public static final int FEATURED_MOVIE = 8;
	public static final int FEATURED_ITEMS = 9;
	public static final int HEADERS = 0;
	
	List<Object> items = new ArrayList<>();
	SparseIntArray itemTypes;
	
	public static HomeDataWrapper wrap(HomeData data) {
		HomeDataWrapper wrapper = new HomeDataWrapper();
		
		wrapper.add("New Releases", HEADERS);
		wrapper.add(data.getNewReleases(), NEW_MOVIES);
		wrapper.add("Upcoming Releases", HEADERS);
		wrapper.add(data.getUpcomingMovies(), UPCOMING_MOVIES);
		
		List<Article> topArticles = data.getTopArticles();
		if (!ListUtils.isEmpty(topArticles)) {
			wrapper.add("Top stories", HEADERS);
			wrapper.add(topArticles.get(0), FEATURED_ARTICLE_HIGHLIGHTED);
			for (int i = 1; i < topArticles.size(); i++) {
				wrapper.add(topArticles.get(i), FEATURED_ARTICLE);
			}
		}
		
		wrapper.add(data.getFeaturedPoll(), FEATURED_POLL);
		wrapper.add(data.getFeaturedTrivia(), FEATURED_TRIVIA);
		
		List<Troll> trolls = data.getFeaturedTrolls();
		if (!ListUtils.isEmpty(trolls)) {
			for (Troll troll : trolls) {
				wrapper.add(troll, FEATURED_TROLL);
			}
		}
		
		List<Movie> movies = data.getFeaturedMovies();
		wrapper.add("Watch movies", HEADERS);
		if (!ListUtils.isEmpty(movies)) {
			for (Movie movie : movies) {
				wrapper.add(movie, FEATURED_MOVIE);
			}
		}
		
		return wrapper;
	}
	
	private void add(Object item, int type) {
		if (item != null) {
			itemTypes.put(items.size(), type);
			items.add(item);
		}
	}
	
	public int addMovie(Movie movie) {
		int initialSize = getItemCount();
		boolean isHeaderAdded = false;
		for (int i = 0; i < items.size(); i++) {
			int type = itemTypes.get(i);
			if (type == HEADERS && items.get(i).equals("Watch movies")) {
				isHeaderAdded = true;
				break;
			}
		}
		
		if (!isHeaderAdded) {
			add("Watch movies", HEADERS);
		}
		
		add(movie, FEATURED_MOVIE);
		return initialSize;
	}
	
	public <T> T getItem(int position) {
		return (T) items.get(position);
	}
	
	public int addMovies(List<Movie> movies) {
		int initialSize = getItemCount();
		for (Movie movie : movies) {
			addMovie(movie);
		}
		return initialSize;
	}
	
	public int getItemCount() {
		return items.size();
	}
	
	public int getType(int position) {
		return itemTypes.get(position);
	}
}
