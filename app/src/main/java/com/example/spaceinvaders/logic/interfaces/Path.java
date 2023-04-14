package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

public interface Path {
    enum PathType{
        SMALL_RECT, TRIANGLE, DTRIANGLE, STABLE, BIG_RECT, DIAMOND, HEXAGON
    }

    interface PathFactory{
        Path produce(PathType type, Point start);
    }
    Point get(int i);
    int getSize();
}
