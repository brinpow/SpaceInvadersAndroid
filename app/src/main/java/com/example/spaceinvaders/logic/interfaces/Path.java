package com.example.spaceinvaders.logic.interfaces;

import android.graphics.Point;

public interface Path {
    enum PathType{ //TODO create more path types
        RECTANGLE, TRIANGLE,
    }

    interface PathFactory{
        Path produce(PathType type);
    }
    Point get(int i);
}
