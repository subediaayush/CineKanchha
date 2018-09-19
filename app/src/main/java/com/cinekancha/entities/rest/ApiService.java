package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieDetail;

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

    @GET("api/home")
    Observable<HomeData> getHomeData();

    @GET("bins/19gt0m")
    Observable<HomeData> getHomeTestData();
}
