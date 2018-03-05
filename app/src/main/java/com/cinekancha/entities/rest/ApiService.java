package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by paoneking on 3/3/18.
 */

public interface ApiService {
    @GET("bins/rt8nt")
    Observable<Movie> getMovie();
}
