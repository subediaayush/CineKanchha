package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.FullMovies;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.UpcomingMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class GetDataRepository {
    private static GetDataRepository INSTANCE;

    public static GetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GetDataRepository();
        }
        return INSTANCE;
    }


    public Observable<List<PollDatabase>> getPollDatabase() {
        List<PollDatabase> data = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults object = realm.where(PollDatabase.class).findAll();
        if (object != null) {
            data = realm.copyToRealm(object);
            return Observable.just(data);
        } else
            return Observable.just(data);
    }

}
