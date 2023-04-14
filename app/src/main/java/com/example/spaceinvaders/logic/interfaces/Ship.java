package com.example.spaceinvaders.logic.interfaces;

import android.content.Context;
import android.graphics.Point;

import com.example.spaceinvaders.gui.Drawable;

import java.util.List;

public interface Ship extends Drawable, Shape, Observable {
    interface ShipFactory{
        Ship produce(Context context, Point pos,BulletsSupplier bulletsSupplier, ShipMoveProvider provider);
    }
    interface ShipMoveProvider{
        Point newPos(Point pos, Point v, Point limit, boolean movable);
    }
    void move(boolean movable);
    List<Bullet> shoot();
    void upgrade();
    void recenter();
    void changeHp(int amount);
    int getHp();
    boolean isAlive();
    void setBarrier();
    void barrierTick();
}
