package com.example.spaceinvaders.database;

import androidx.room.RoomDatabase;

public abstract class AppDataBase extends RoomDatabase {
    public abstract HighScoresDao highScoresDao();
    public abstract AchievementDao achievementDao();
}
