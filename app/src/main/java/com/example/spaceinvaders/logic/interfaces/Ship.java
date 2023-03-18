package com.example.spaceinvaders.logic.interfaces;

import com.example.spaceinvaders.gui.Drawable;

import java.util.List;

public interface Ship extends Drawable, Shape {
    void move(float x, float y);
    List<Bullet> shoot();
    void upgrade();
    void recenter();
}
