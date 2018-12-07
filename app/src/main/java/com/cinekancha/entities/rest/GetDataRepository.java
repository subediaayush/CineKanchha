package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.model.UserPoll;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import io.reactivex.Single;

public class GetDataRepository {
    private static GetDataRepository INSTANCE;

    public static GetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GetDataRepository();
        }
        return INSTANCE;
    }


    public Single<List<UserPoll>> getPollDatabase() {
        FutureTask<List<UserPoll>> task = new FutureTask<>(new Callable<List<UserPoll>>() {
            @Override
            public List<UserPoll> call() {
                return PollDatabase.getDatabase().dao().get();
            }
        });
        Executors.newSingleThreadExecutor().execute(task);
        return Single.fromFuture(task);
    }

}
