package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.MovieDetail;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineNewsGossipsViewModel extends AndroidViewModel {

    private MovieDetail mMovie;
    private int mMovieId;


    public CineNewsGossipsViewModel(@NonNull Application application) {
        super(application);
    }

    public MovieDetail getMovie() {
        return mMovie;
    }

    public void setMovie(MovieDetail movie) {
        this.mMovie = movie;
    }

    public void setMovieID(int movieID) {
        mMovieId = movieID;
    }

    public int getMovieId() {
        return mMovieId;
    }
}
