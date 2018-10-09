package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorPhoto;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineActorViewModel extends AndroidViewModel {

    private List<Actor> actorList;

    public CineActorViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

}
