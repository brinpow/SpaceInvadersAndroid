package com.example.spaceinvaders.logic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Ship;

public class ShipFactoryImpl implements Ship.ShipFactory {
    @Override
    public Ship produce(Context context, Point pos, BulletsSupplier bulletsSupplier, Ship.ShipMoveProvider provider) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        return new ShipImpl(BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship),pos, new Point(screenWidth/20,screenHeight/35), bulletsSupplier, 20, provider);
    }
}
