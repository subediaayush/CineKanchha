package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Video;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTrendingViewModel extends BasePaginationViewModel {

    private List<Video> trendingList;
    private List<Video> appendTrendingList;

    public CineTrendingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        trendingList = null;
        appendTrendingList = null;
    }

    public List<Video> getTrendingList() {
        return trendingList;
    }

    public void setTrendingList(List<Video> trendingList) {
        if (this.trendingList == null) {
            this.trendingList = trendingList;
        }
        else
            this.trendingList.addAll(trendingList);
    }

    public List<Video> getAppendTrendingList() {
        return appendTrendingList;
    }

    public void setAppendTrendingList(List<Video> appendTrendingList) {
        this.appendTrendingList = appendTrendingList;
    }
}
