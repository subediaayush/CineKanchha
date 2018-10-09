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

public class CineActorPhotoViewModel extends AndroidViewModel {

    private ActorPhoto actor;
    private int actorID;

    public CineActorPhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public ActorPhoto getActorPhoto() {
        return actor;
    }

    public void setActorPhoto(ActorPhoto actor) {
        this.actor = actor;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public int getActorID() {
        return actorID;
    }
}
