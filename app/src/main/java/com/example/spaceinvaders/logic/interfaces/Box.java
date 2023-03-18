package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

import com.example.spaceinvaders.gui.Drawable;

public interface Box extends Drawable, Shape {
    enum BoxType{
        UPGRADE, HEAL,
    }

    interface BoxFactory{
        Box produce(Point pos, BoxType type);
    }

    interface OpenBoxFunction{
        void open();
    }

    void move();
    boolean isOnScreen(int screenWidth, int screenHeight);
    void openBox();
}
