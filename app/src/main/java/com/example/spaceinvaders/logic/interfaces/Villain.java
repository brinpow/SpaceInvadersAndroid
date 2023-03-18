package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

import com.example.spaceinvaders.gui.Drawable;

import java.util.List;

public interface Villain extends Shape, Drawable {
    enum VillainType{
     v1(10), v2(20), v3(30), v4(40), v5(50), v6(60), v7(70);
        private final int score;
        VillainType(int score){
            this.score = score;
        }

        public int getScore(){
            return score;
        }
    }
    interface VillainFactory{
        Villain produce(Point pos, VillainType type, BulletsSupplier bulletsSupplier);
    }
    void dealDamage(int damage);
    boolean isAlive();
    void move();
    Point getPosition();
    int getScore();
    List<Bullet> shoot();
}
