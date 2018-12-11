package com.cinekancha.entities.model;


import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "polls")
public class UserPoll implements Serializable {
    
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
