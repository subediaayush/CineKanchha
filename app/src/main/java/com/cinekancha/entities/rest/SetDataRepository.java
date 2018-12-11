package com.cinekancha.entities.rest;

import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.model.UserPoll;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import io.reactivex.Single;


public class SetDataRepository {
    private static SetDataRepository INSTANCE;

    public static SetDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SetDataRepository();
        }
        return INSTANCE;
    }

    public Single<List<Long>> setPollDatabase(UserPoll data) {
        FutureTask<List<Long>> task = new FutureTask<>(new Callable<List<Long>>() {
            @Override
            public List<Long> call() {
                return PollDatabase.getDatabase().dao().save(data);
            }
        });
        Executors.newSingleThreadExecutor().execute(task);
        return Single.fromFuture(task);
        
        
    }

}
