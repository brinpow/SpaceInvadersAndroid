package com.example.spaceinvaders.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AchievementDao { //TODO implement update method properly
    @Insert
    void insert();

    @Update
    void updateRecord(Achievement achievement);

    @Query("SELECT * FROM achievements")
    List<Achievement> getAllAchievements();
}
