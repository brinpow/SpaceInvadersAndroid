package com.example.spaceinvaders.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements")
public class Achievement {
    @PrimaryKey()
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "value")
    private int value;
}
