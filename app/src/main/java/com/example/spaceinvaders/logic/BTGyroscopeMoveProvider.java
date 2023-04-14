package com.example.spaceinvaders.logic;

import android.content.Context;
import android.graphics.Point;

import com.example.spaceinvaders.bluetooth.TransferService;
import com.example.spaceinvaders.managers.GyroscopeManager;

import java.nio.ByteBuffer;

public class BTGyroscopeMoveProvider extends GyroscopeMoveProvider{
    private final Context context;

    public BTGyroscopeMoveProvider(GyroscopeManager gyroscopeManager, Context context) {
        super(gyroscopeManager);
        this.context = context;
    }

    public Point newPos(Point pos, Point v, Point limit, boolean movable){
        Point nPos = super.newPos(pos,v,limit, movable);
        TransferService.write(context, ByteBuffer.allocate(4).putInt(nPos.x).array());
        TransferService.write(context, ByteBuffer.allocate(4).putInt(nPos.y).array());
        return nPos;
    }
}
