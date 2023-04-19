package com.example.spaceinvaders.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface HighScoresDao {
    @Insert
    Completable insert(HighScore highScore);

    @Query("SELECT * FROM high_scores ORDER BY highScore DESC")
    Single<List<HighScore>> getHighScores();

    @Query("SELECT * FROM high_scores ORDER BY highScore DESC")
    Flowable<List<HighScore>> getAllHighScores();

    @Delete
    Completable delete(HighScore highScore);
}
