package com.example.spaceinvaders.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoresDao {
    @Insert
    void insert(HighScore highScore);

    @Query("SELECT * FROM high_scores ORDER BY highScore DESC")
    List<HighScore> getAllHighScores();

    @Delete
    void delete(HighScore highScore);
}
