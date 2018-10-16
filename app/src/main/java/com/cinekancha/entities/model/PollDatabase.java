package com.cinekancha.entities.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PollDatabase extends RealmObject {
    @PrimaryKey
    private long pollId;
    private long optionId;

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getPollId() {

        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }
}
