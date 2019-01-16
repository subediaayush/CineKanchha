package com.cinekancha.view;

import android.app.Application;

import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.entities.model.MovieDetail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineBoxOfficeViewModel extends AndroidViewModel {

    private List<MovieBoxOffice> boxOffice;
    private String movieId;
    private MovieDetail mMovie;


    public CineBoxOfficeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<MovieBoxOffice> getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(List<MovieBoxOffice> boxOffice) {
        this.boxOffice = boxOffice;
    }
}
