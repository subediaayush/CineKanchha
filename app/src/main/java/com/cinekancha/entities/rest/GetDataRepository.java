package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.UpcomingMovie;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;


public class GetDataRepository {
    private static GetDataRepository INSTANCE;

    public static GetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GetDataRepository();
        }
        return INSTANCE;
    }

    public Observable<HomeData> getHomeData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(HomeData.class).findFirst();
        if (object != null) {
            return Observable.just((HomeData) object);
        } else
            return Observable.just(new HomeData());
    }

    public Observable<UpcomingMovie> getUpcomingData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(UpcomingMovie.class).findFirst();
        if (object != null) {
            return Observable.just((UpcomingMovie) object);
        } else
            return Observable.just(new UpcomingMovie());
    }

    public Observable<Trivia> getTrivia() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(Trivia.class).findFirst();
        if (object != null) {
            return Observable.just((Trivia) object);
        } else
            return Observable.just(new Trivia());
    }

    public Observable<NewsGossip> getNewsGossip() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(NewsGossip.class).findFirst();
        if (object != null) {
            return Observable.just((NewsGossip) object);
        } else
            return Observable.just(new NewsGossip());
    }

    public Observable<MovieDetail> getMovieDetail(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(MovieDetail.class).equalTo("id", id).findFirst();
        if (object != null) {
            return Observable.just((MovieDetail) object);
        } else
            return Observable.just(new MovieDetail());
    }
}
