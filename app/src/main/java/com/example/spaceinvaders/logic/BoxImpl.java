package com.example.spaceinvaders.logic;

import android.graphics.Bitmap;
import android.graphics.Point;

public abstract class BoxImpl extends ShapeImpl implements com.example.spaceinvaders.logic.interfaces.Box {
    private final float vX;
    private final float vY;

    public BoxImpl(Point pos, float vX, float vY, Bitmap image) {
        super(pos, image);
        this.vX = vX;
        this.vY = vY;
    }

    @Override
    public void move() {
        posX += vX;
        posY += vY;
    }

    @Override
    public boolean isOnScreen(int screenWidth, int screenHeight) {
        return 0<=posX && posX<=screenWidth - image.getWidth() && 0<=posY && posY<=screenHeight-image.getHeight();
    }

}
