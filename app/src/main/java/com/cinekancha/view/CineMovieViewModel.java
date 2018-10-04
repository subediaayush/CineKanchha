package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.Trivia;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineMovieViewModel extends AndroidViewModel {

    private List<Movie> movieList;

    public CineMovieViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
