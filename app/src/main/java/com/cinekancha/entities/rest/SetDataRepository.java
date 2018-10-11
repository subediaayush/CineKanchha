package com.cinekancha.entities.rest;

import android.util.Log;

import com.cinekancha.entities.Video;
import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Meta;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PageLink;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.TriviaData;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.entities.model.UpcomingMovie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class SetDataRepository {
    private static SetDataRepository INSTANCE;

    public static SetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SetDataRepository();
        }
        return INSTANCE;
    }

    public Observable<HomeData> setHomeData(HomeData data) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> {
                    realm1.delete(HomeData.class);
                    realm1.delete(FeaturedContent.class);
                    realm1.delete(TrollData.class);
                    realm1.delete(Movie.class);
                    realm1.delete(BoxOfficeItem.class);
                    realm1.delete(Article.class);
                    realm1.delete(Video.class);
                    realm1.delete(PollData.class);
                    realm1.delete(Option.class);
                    realm1.delete(TriviaData.class);
                    data.setId(0);
                    realm1.copyToRealmOrUpdate(data);
                }, () -> Log.d("Success", "Data saved")
                , error -> Log.d("Error", "Data saved error"));
        return Observable.just(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MovieDetail> setMovieDetail(MovieDetail data) {
        data.getCrew().setId(data.getId());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        return Observable.just(data);
    }

    public Observable<MovieData> setMovie(MovieData data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(MovieData.class);
                        realm1.delete(Movie.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<List<Movie>> setNewRelease(List<Movie> data) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        return Observable.just(data);
    }

    public Observable<UpcomingMovie> setUpcomingMovie(UpcomingMovie data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(UpcomingMovie.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<List<BoxOfficeItem>> setBoxOffice(List<BoxOfficeItem> data) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        return Observable.just(data);
    }

    public Observable<NewsGossip> setNewsGossip(NewsGossip data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(NewsGossip.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<Troll> setTroll(Troll data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(Troll.class);
                        realm1.delete(TrollData.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<TrendingData> setTrending(TrendingData data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(TrendingData.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Video.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<ActorGallery> setActorList(ActorGallery data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(Actor.class);
                        realm1.delete(ActorGallery.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<ActorPhoto> setActorPhoto(ActorPhoto data) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        return Observable.just(data);
    }

    public Observable<TrendingData> setFullMovies(TrendingData data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(TrendingData.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Video.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<Trivia> setTriviaData(Trivia data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(Trivia.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(TriviaData.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

    public Observable<Poll> setPoll(Poll data) {
        Realm realm = Realm.getDefaultInstance();
        if (data.getMeta().getCurrentPage() != 1) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
        } else {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.delete(TrendingData.class);
                        realm1.delete(PageLink.class);
                        realm1.delete(Video.class);
                        realm1.delete(Meta.class);
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> Log.d("Success", "Data saved")
                    , error -> Log.d("Error", "Data saved error"));
        }
        return Observable.just(data);
    }

}
