package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.PollData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CinePollViewModel extends BasePaginationViewModel {

    private List<PollData> pollDataList;
    private List<PollData> appendPollDataList;

    public CinePollViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        pollDataList = null;
        appendPollDataList = null;
    }

    public List<PollData> getPollDataList() {
        return pollDataList;
    }

    public void setPollDataList(List<PollData> pollDataList) {
        if (this.pollDataList == null)
            this.pollDataList = pollDataList;
        else this.pollDataList.addAll(pollDataList);
    }

    public List<PollData> getAppendPollDataList() {
        return appendPollDataList;
    }

    public void setAppendPollDataList(List<PollData> appendPollDataList) {
        this.appendPollDataList = appendPollDataList;
    }
}
