package com.example.spaceinvaders.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AchievementDao {
    @Insert
    void insert(Achievement achievement);

    @Query("UPDATE achievements SET value = :value WHERE name = :name")
    void updateRecord(String name, int value);

    @Query("SELECT * FROM achievements")
    List<Achievement> getAllAchievements();

    @Query("SELECT * FROM achievements WHERE name = :name")
    Achievement getAchievement(String name);
}
