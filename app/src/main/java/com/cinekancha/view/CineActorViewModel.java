package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.Actor;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineActorViewModel extends BasePaginationViewModel {

    private List<Actor> actorList;
    private List<Actor> appendActorList;

    public CineActorViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        actorList = null;
        appendActorList = null;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        if (this.actorList == null)
            this.actorList = actorList;
        else this.actorList.addAll(actorList);
    }

    public List<Actor> getAppendActorList() {
        return appendActorList;
    }

    public void setAppendActorList(List<Actor> appendActorList) {
        this.appendActorList = appendActorList;
    }
}
