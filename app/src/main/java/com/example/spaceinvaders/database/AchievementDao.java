package com.example.spaceinvaders.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AchievementDao {
    @Insert
    Completable insert(Achievement achievement);

    @Query("UPDATE achievements SET value = :value WHERE name = :name")
    Completable updateRecord(String name, int value);

    @Query("SELECT * FROM achievements")
    Single<List<Achievement>> getAchievements();

    @Query("SELECT * FROM achievements")
    Flowable<List<Achievement>> getAllAchievements();

    @Query("SELECT * FROM achievements WHERE name = :name")
    Single<Achievement> getAchievement(String name);
}
