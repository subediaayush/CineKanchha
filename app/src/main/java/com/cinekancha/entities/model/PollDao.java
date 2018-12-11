package com.cinekancha.entities.model;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PollDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> save(UserPoll... data);
    
    @Query("SELECT * FROM polls")
    List<UserPoll> get();
}
