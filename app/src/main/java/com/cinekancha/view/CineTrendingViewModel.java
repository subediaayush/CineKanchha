package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Video;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTrendingViewModel extends AndroidViewModel {

    private List<Video> trendingList;

    public CineTrendingViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Video> getTrendingList() {
        return trendingList;
    }

    public void setTrendingList(List<Video> trendingList) {
        this.trendingList = trendingList;
    }
}
