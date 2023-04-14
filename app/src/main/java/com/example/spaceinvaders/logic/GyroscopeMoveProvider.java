package com.example.spaceinvaders.logic;

import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Ship;
import com.example.spaceinvaders.managers.GyroscopeManager;

public class GyroscopeMoveProvider implements Ship.ShipMoveProvider {
    private final GyroscopeManager gyroscopeManager;

    public GyroscopeMoveProvider(GyroscopeManager gyroscopeManager){
        this.gyroscopeManager = gyroscopeManager;
    }

    private float newPosX(float pX, float x, float vX, float limit){
        if(Math.abs(x)>0.15) {
            if (Math.abs(x) < 0.35) {
                pX += x * vX;
            } else {
                pX += x * vX / 3;
            }
            pX = Math.min(Math.max(pX, 0), limit);
            return pX;
        }
        return pX;
    }

    @Override
    @SuppressWarnings("all")
    public Point newPos(Point pos, Point v, Point limit, boolean movable) {
        float[] gyroscopeValues = gyroscopeManager.getGyroscopeValues();
        if(gyroscopeValues==null || !movable){
            return pos;
        }

        Point newShipPos = new Point();
        newShipPos.x = (int) newPosX(pos.x, gyroscopeValues[1],v.x, limit.x);
        newShipPos.y = (int) newPosX(pos.y, gyroscopeValues[0], v.y, limit.y);

        return newShipPos;
    }
}
