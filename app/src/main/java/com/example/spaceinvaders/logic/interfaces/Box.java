package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

import com.example.spaceinvaders.gui.Drawable;

import java.util.List;

public interface Box extends Drawable, Shape {
    enum BoxType{
        UPGRADE, HEAL,
    }

    interface BoxFactory{
        Box produce(Point pos, BoxType type);
    }

    interface OpenBoxFunction{
        void open(Ship ship);
    }

    interface BoxSpawner{
        List<Box> create(Point pos);
    }

    void move();
    boolean isOnScreen(int screenWidth, int screenHeight);
    void openBox(Ship ship);
}
