package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.MovieDetail;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineBoxOfficeViewModel extends AndroidViewModel {

    private List<BoxOfficeItem> boxOffice;
    private String movieId;
    private MovieDetail mMovie;


    public CineBoxOfficeViewModel(@NonNull Application application) {
        super(application);
    }

    public List<BoxOfficeItem> getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(List<BoxOfficeItem> boxOffice) {
        this.boxOffice = boxOffice;
    }
}
