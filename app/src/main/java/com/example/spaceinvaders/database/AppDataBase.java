package com.example.spaceinvaders.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Achievement.class, HighScore.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE = null;

    public abstract HighScoresDao getHighScoresDao();
    public abstract AchievementDao getAchievementDao();
    public static AppDataBase getDB(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "achievements").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
