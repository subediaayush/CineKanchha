package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.Video;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineFullMoviesViewModel extends BasePaginationViewModel {

    private List<Video> videoList;
    private List<Video> appendVideoList;

    public CineFullMoviesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        videoList = null;
        appendVideoList = null;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        if (this.videoList == null)
            this.videoList = videoList;
        else
            this.videoList.addAll(videoList);
    }

    public List<Video> getAppendVideoList() {
        return appendVideoList;
    }

    public void setAppendVideoList(List<Video> appendVideoList) {
        this.appendVideoList = appendVideoList;
    }
}
