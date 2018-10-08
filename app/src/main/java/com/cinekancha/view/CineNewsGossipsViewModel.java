package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.NewsGossip;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineNewsGossipsViewModel extends AndroidViewModel {
    private NewsGossip mNewsGossip;

    public CineNewsGossipsViewModel(@NonNull Application application) {
        super(application);
    }

    public NewsGossip getNewsGossip() {
        return mNewsGossip;
    }

    public void setNewsGossip(NewsGossip newsGossip) {
        this.mNewsGossip = newsGossip;
    }
}
