package com.cinekancha.home;

import android.util.SparseIntArray;

import com.cinekancha.entities.model.GalleryItem;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.entities.model.Video;
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
    public static final int FEATURED_BOX_OFFICE = 9;
    public static final int FEATURED_PHOTO_GALLERY = 10;
    public static final int FEATURED_REVIEWS = 11;
    public static final int FEATURED_SHOWTIMES = 12;
    public static final int FEATURED_TRENDING_VIDEOS = 13;
    public static final int FEATURED_FULL_VIDEOS = 14;
    public static final int FEATURED_TOP_STORIES = 15;
    public static final int ALL_MOVIES = 16;
    public static final int HEADERS = 0;

    List<Object> items = new ArrayList<>();
    SparseIntArray itemTypes = new SparseIntArray();

    public static HomeDataWrapper wrap(HomeData data) {
        HomeDataWrapper wrapper = new HomeDataWrapper();

        wrapper.add(data.getNewReleases(), NEW_MOVIES);

        wrapper.add(data.getHotNews(), FEATURED_ARTICLE);

        if (data.getTopStories() != null && !data.getTopStories().isEmpty()) {
            wrapper.add(data.getTopStories(), FEATURED_TOP_STORIES);
        } else if (data.getTopStory() != null && data.getTopStory().getSummary() != null) {
            wrapper.add(data.getTopStory(), FEATURED_TOP_STORIES);
        }

        wrapper.add(data.getMovies(), ALL_MOVIES);
        wrapper.add(data.getUpComingMovies(), UPCOMING_MOVIES);

        List<MovieBoxOffice> boxOfficeItems = data.getBoxOfficeList();
        if (!ListUtils.isEmpty(boxOfficeItems)) {
            wrapper.add(boxOfficeItems, FEATURED_BOX_OFFICE);
        }

        List<GalleryItem> photoGallery = data.getPhotoGallery();
        if (!ListUtils.isEmpty(photoGallery)) {
            wrapper.add(photoGallery, FEATURED_PHOTO_GALLERY);
        }

        wrapper.add(data.getPoll(), FEATURED_POLL);

        List<Movie> reviews = data.getFeaturedReviews();
        if (!ListUtils.isEmpty(reviews)) {
            wrapper.add(reviews, FEATURED_REVIEWS);
        }

        List<Video> trendingVideo = data.getTrendingVideo();
        if (!ListUtils.isEmpty(trendingVideo)) {
            wrapper.add(trendingVideo, FEATURED_TRENDING_VIDEOS);
        }

        wrapper.add(data.getTriviaData(), FEATURED_TRIVIA);

        List<Video> fullMovies = data.getFullMovies();
        if (!ListUtils.isEmpty(fullMovies)) {
            wrapper.add(fullMovies, FEATURED_FULL_VIDEOS);
        }


        wrapper.add(data.getTroll(), FEATURED_TROLL);
//
////		List<Movie> movies = data.getFeaturedMovies();
////		wrapper.add("Watch movies", HEADERS);
////		if (!ListUtils.isEmpty(movies)) {
////			for (Movie movie : movies) {
////				wrapper.add(movie, FEATURED_MOVIE);
////			}
////		}

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
