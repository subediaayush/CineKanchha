package com.cinekancha.entities.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PollDatabase extends RealmObject {
    @PrimaryKey
    private int pollId;
    private int optionId;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getPollId() {

        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
}
