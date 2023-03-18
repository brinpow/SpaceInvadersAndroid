package com.example.spaceinvaders.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.spaceinvaders.gui.Drawable;
import com.example.spaceinvaders.logic.interfaces.Shape;

public class ShapeImpl implements Shape, Drawable {
    protected float posX;
    protected float posY;
    protected final Bitmap image;

    public ShapeImpl(Point pos, Bitmap image){
        posX = pos.x - (float)(image.getWidth()/2);
        posY = pos.y - (float)(image.getHeight()/2);
        this.image = image;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, posX, posY, null);
    }

    @Override
    public Rect getShape() {
        return new Rect((int) posX, (int) posY, (int) (posX + image.getWidth()), (int) (posY+image.getHeight()));
    }

    @Override
    public boolean intersects(Shape shape) {
        return shape.getShape().intersect(getShape());
    }
}
