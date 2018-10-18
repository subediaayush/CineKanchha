package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.FullMovies;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.UpcomingMovie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by paoneking on 3/3/18.
 */

public interface ApiService {
    @GET("api/movie/{movieId}")
    Observable<MovieDetail> getMovie(
            @Path("movieId") int movieId);

    @GET("api/movie")
    Observable<MovieData> getMovieList(@Query("page") int currentPage);

    @GET("api/new_releases")
    Observable<NewRelease> getNewRelease(@Query("page") int currentPage);

    @GET("api/upcoming_movies")
    Observable<UpcomingMovie> getUpcomingMovie(@Query("page") int currentPage);

    @GET("api/home")
    Observable<HomeData> getHomeData();

    @GET("api/boxOffice")
    Observable<List<BoxOfficeItem>> getBoxOffice();

    @GET("api/news")
    Observable<NewsGossip> getNewsGossip(@Query("page") int currentPage);

    @GET("api/troll")
    Observable<Troll> getTroll(@Query("page") int currentPage);

    @GET("api/trending_videos")
    Observable<TrendingData> getTrending(@Query("page") int currentPage);

    @GET("api/actorGallery")
    Observable<ActorGallery> getActorList(@Query("page") int currentPage);

    @GET("api/movie_reviews")
    Observable<Reviews> getReviews(@Query("page") int currentPage);

    @GET("api/actorGallery/{actorId}")
    Observable<ActorPhoto> getActorPhoto(
            @Path("actorId") int actorId);

    @GET("api/full_movies")
    Observable<FullMovies> getFullMovies(@Query("page") int currentPage);

    @GET("api/poll")
    Observable<Poll> getPoll(@Query("page") int currentPage);

    @GET("api/poll/upvote/{id}")
    Observable<Response<Void>> postPoll(@Path("id") long optionId);

    @GET("api/trivia")
    Observable<Trivia> getTrivia(@Query("page") int currentPage);

    @GET("bins/19gt0m")
    Observable<HomeData> getHomeTestData();
}
