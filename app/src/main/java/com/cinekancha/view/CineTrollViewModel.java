package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTrollViewModel extends AndroidViewModel {

    private List<TrollData> mTrolls;

    public CineTrollViewModel(@NonNull Application application) {
        super(application);
    }

    public List<TrollData> getTrolls() {
        return mTrolls;
    }

    public void setArticles(List<TrollData> trolls) {
        this.mTrolls = trolls;
    }
}
