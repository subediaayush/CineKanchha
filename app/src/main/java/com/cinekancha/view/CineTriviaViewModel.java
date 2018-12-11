package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.TriviaData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTriviaViewModel extends BasePaginationViewModel {

    private List<TriviaData> triviaDataList;
    private List<TriviaData> appendTriviaDataList;

    public CineTriviaViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        triviaDataList = null;
        appendTriviaDataList = null;
    }

    public List<TriviaData> getTriviaDataList() {
        return triviaDataList;
    }

    public void setTriviaDataList(List<TriviaData> triviaDataList) {
        if (this.triviaDataList == null)
            this.triviaDataList = triviaDataList;
        else this.triviaDataList.addAll(triviaDataList);
    }

    public List<TriviaData> getAppendTriviaDataList() {
        return appendTriviaDataList;
    }

    public void setAppendTriviaDataList(List<TriviaData> appendTriviaDataList) {
        this.appendTriviaDataList = appendTriviaDataList;
    }
}
