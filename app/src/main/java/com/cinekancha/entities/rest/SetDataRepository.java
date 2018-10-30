package com.cinekancha.entities.rest;

import android.util.Log;

import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.FullMovies;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Meta;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PageLink;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.TriviaData;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.entities.model.UpcomingMovie;
import com.cinekancha.entities.model.Video;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;

public class SetDataRepository {
    private static SetDataRepository INSTANCE;

    public static SetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SetDataRepository();
        }
        return INSTANCE;
    }

    public Single<PollDatabase> setPollDatabase(PollDatabase data) {
        Realm realm = Realm.getDefaultInstance();

        return Single.create(e -> {
            realm.executeTransactionAsync(realm1 -> {
                        realm1.copyToRealmOrUpdate(data);
                    }, () -> {
                        e.onSuccess(data);
                        Log.d("SuccessPoll", "Data saved");
                    }
                    , error -> Log.d("Error", "Data saved error"));
        });
    }

}
