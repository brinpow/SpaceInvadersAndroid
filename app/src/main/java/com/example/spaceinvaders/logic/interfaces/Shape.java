package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Rect;

public interface Shape {
    Rect getShape();
    boolean intersects(Shape shape);
}
