package com.example.spaceinvaders.logic;

import android.content.Context;
import android.graphics.Point;
import com.example.spaceinvaders.bluetooth.TransferService;
import com.example.spaceinvaders.logic.interfaces.Ship;

public class BluetoothMoveProvider implements Ship.ShipMoveProvider {
    private final Context context;

    public BluetoothMoveProvider(Context context){
        this.context = context;
    }
    @Override
    public Point newPos(Point pos, Point v, Point limit, boolean movable) {
        int posX = TransferService.read(context);
        int posY = TransferService.read(context);

        return new Point(posX, posY);
    }
}
