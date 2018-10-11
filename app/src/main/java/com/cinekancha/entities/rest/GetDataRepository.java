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

    public Observable<Reviews> getReviews() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(Reviews.class).findFirst();
        if (object != null) {
            return Observable.just((Reviews) object);
        } else
            return Observable.just(new Reviews());
    }

    public Observable<ActorGallery> getActorGallery() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(ActorGallery.class).findFirst();
        if (object != null) {
            return Observable.just((ActorGallery) object);
        } else
            return Observable.just(new ActorGallery());
    }

    public Observable<ActorPhoto> getActorPhoto() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(ActorPhoto.class).findFirst();
        if (object != null) {
            return Observable.just((ActorPhoto) object);
        } else
            return Observable.just(new ActorPhoto());
    }

    public Observable<MovieData> getMovieData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(MovieData.class).findFirst();
        if (object != null) {
            return Observable.just((MovieData) object);
        } else
            return Observable.just(new MovieData());
    }

    public Observable<NewRelease> getNewReleaseData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(NewRelease.class).findFirst();
        if (object != null) {
            return Observable.just((NewRelease) object);
        } else
            return Observable.just(new NewRelease());
    }

    public Observable<Poll> getPollData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(Poll.class).findFirst();
        if (object != null) {
            return Observable.just((Poll) object);
        } else
            return Observable.just(new Poll());
    }

    public Observable<FullMovies> getFullMovies() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(FullMovies.class).findFirst();
        if (object != null) {
            return Observable.just((FullMovies) object);
        } else
            return Observable.just(new FullMovies());
    }

    public Observable<TrendingData> getTrendingData() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(TrendingData.class).findFirst();
        if (object != null) {
            return Observable.just((TrendingData) object);
        } else
            return Observable.just(new TrendingData());
    }

    public Observable<Troll> getTroll() {
        Realm realm = Realm.getDefaultInstance();
        RealmObject object = realm.where(Troll.class).findFirst();
        if (object != null) {
            return Observable.just((Troll) object);
        } else
            return Observable.just(new Troll());
    }

    public Observable<List<BoxOfficeItem>> getBoxOffice() {
        List<BoxOfficeItem> data = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults object = realm.where(BoxOfficeItem.class).findAll();
        if (object != null) {
            data = realm.copyToRealm(object);
            return Observable.just(data);
        } else
            return Observable.just(data);
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
