package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.BoxOffice;
import com.cinekancha.entities.model.MovieDetail;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineBoxOfficeViewModel extends AndroidViewModel {

    private BoxOffice boxOffice;
    private String movieId;
    private MovieDetail mMovie;


    public CineBoxOfficeViewModel(@NonNull Application application) {
        super(application);
    }

    public BoxOffice getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOffice boxOffice) {
        this.boxOffice = boxOffice;
    }

    public MovieDetail getMovie() {
        return mMovie;
    }

    public void setMovie(MovieDetail movie) {
        this.mMovie = movie;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }
}
