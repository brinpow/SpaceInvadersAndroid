package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

import com.example.spaceinvaders.gui.Drawable;

public interface Bullet extends Drawable, Shape {
    enum BulletType{
        COMMON, RARE, EPIC, VILLAIN
    }
    interface BulletFactory{
        Bullet produce(Point pos, float vX, float vY, BulletType type);
    }
    void move();
    boolean isOnScreen(float screenWidth, float screenHeight);
    int getDamageValue();
}
