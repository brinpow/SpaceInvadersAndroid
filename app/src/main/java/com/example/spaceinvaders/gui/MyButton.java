package com.example.spaceinvaders.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

public class MyButton
{
    public interface ClickFunction{
        void click(boolean value);
    }
    private final Matrix matrix = new Matrix();
    private final RectF rect;
    private final ClickFunction clickFunction;
    private final Bitmap image;

    public MyButton(int posX, int posY, ClickFunction clickFunction, Bitmap image)
    {
        this.image = image;
        this.clickFunction = clickFunction;
        int x = posX - image.getWidth() - 25;
        int y = posY + 3*image.getHeight();
        rect = new RectF(0, 0, 2*image.getWidth(), 2*image.getHeight());
        matrix.setTranslate(x, y);
        matrix.mapRect(rect);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, matrix, null);
    }

    public void press(float x, float y, boolean pressed){
        if(rect.contains(x, y) && pressed){
            clickFunction.click(true);
        }
        else if(!pressed){
            clickFunction.click(false);
        }
    }
}
