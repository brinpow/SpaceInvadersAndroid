package com.example.spaceinvaders.logic;

import android.graphics.Bitmap;
import android.graphics.Point;

public class BulletImpl extends ShapeImpl implements com.example.spaceinvaders.logic.interfaces.Bullet {
    private final float vX;
    private final float vY;
    private final int damage;


    BulletImpl(Point pos, float vX, float vY, Bitmap image, int damage){
        super(pos, image);
        this.vX = vX;
        this.vY = vY;
        this.damage = damage;
    }

    @Override
    public void move() {
        posX += vX;
        posY += vY;
    }

    @Override
    public boolean isOnScreen(float screenWidth, float screenHeight) {
        return 0<=posX && posX<=screenWidth - image.getWidth() && 0<=posY && posY<=screenHeight-image.getHeight();
    }

    @Override
    public int getDamageValue() {
        return damage;
    }
}
