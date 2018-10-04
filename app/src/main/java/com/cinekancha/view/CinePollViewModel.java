package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollData;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CinePollViewModel extends AndroidViewModel {

    private Poll pollData;

    public CinePollViewModel(@NonNull Application application) {
        super(application);
    }

    public Poll getPollData() {
        return pollData;
    }

    public void setPollData(Poll pollData) {
        this.pollData = pollData;
    }
}
