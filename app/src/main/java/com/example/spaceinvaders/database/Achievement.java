package com.example.spaceinvaders.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "achievements")
public class Achievement {
    @PrimaryKey()
    @ColumnInfo(name = "name")
    @NotNull
    private String name;

    @ColumnInfo(name = "value")
    private int value;

    public Achievement(){}
    public Achievement(@NonNull String name, int value){
        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
