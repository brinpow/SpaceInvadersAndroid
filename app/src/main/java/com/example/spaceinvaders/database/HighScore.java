package com.example.spaceinvaders.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "high_scores")
public class HighScore {
    @PrimaryKey
    private int id;

    @ColumnInfo(name="highScore")
    private int highScore;

    public int getId() {
        return id;
    }

    public HighScore(){}
    @Ignore
    public HighScore(int id, int highScore){
        this.id = id;
        this.highScore = highScore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
