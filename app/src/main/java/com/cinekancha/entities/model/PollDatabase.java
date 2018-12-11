package com.cinekancha.entities.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = UserPoll.class, version = 1)
public abstract class PollDatabase extends RoomDatabase {
    
    private static PollDatabase sInstance;
    
    public abstract PollDao dao();
    
    public static void init(Context context) {
        sInstance = Room.databaseBuilder(context.getApplicationContext(), PollDatabase.class, "polls")
                .fallbackToDestructiveMigration()
                .build();
    }
    
    public static PollDatabase getDatabase() {
        return sInstance;
    }
}
