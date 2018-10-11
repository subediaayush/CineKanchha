package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.Reviews;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineReviewViewModel extends AndroidViewModel {

    private Reviews reviews;

    public CineReviewViewModel(@NonNull Application application) {
        super(application);
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews actor) {
        this.reviews = actor;
    }

}
