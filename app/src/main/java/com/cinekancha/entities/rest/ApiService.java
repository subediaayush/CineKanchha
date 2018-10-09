package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Troll;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by paoneking on 3/3/18.
 */

public interface ApiService {
    @GET("api/movie/{movieId}")
    Observable<MovieDetail> getMovie(
            @Path("movieId") int movieId);

    @GET("api/movie")
    Observable<MovieData> getMovieList();

    @GET("api/new_releases")
    Observable<NewRelease> getNewRelease();

    @GET("api/home")
    Observable<HomeData> getHomeData();

    @GET("api/boxOffice")
    Observable<List<BoxOfficeItem>> getBoxOffice();

    @GET("api/news")
    Observable<NewsGossip> getNewsGossip();

    @GET("api/troll")
    Observable<Troll> getTroll();

    @GET("api/trending_videos")
    Observable<TrendingData> getTrending();

    @GET("api/full_movies")
    Observable<TrendingData> getFullMovies();

    @GET("bins/19gt0m")
    Observable<HomeData> getHomeTestData();
}
