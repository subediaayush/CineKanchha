package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.Movie;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineMovieViewModel extends BasePaginationViewModel {

    private List<Movie> movieList;
    private List<Movie> appendMovieList;

    public CineMovieViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        movieList = null;
        appendMovieList = null;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        if (this.movieList == null)
            this.movieList = movieList;
        else this.movieList.addAll(movieList);
    }

    public List<Movie> getAppendMovieList() {
        return appendMovieList;
    }

    public void setAppendMovieList(List<Movie> appendMovieList) {
        this.appendMovieList = appendMovieList;
    }
}
