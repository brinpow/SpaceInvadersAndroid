package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Ship;

import java.util.Map;
import java.util.Objects;


public class BoxFactoryImpl implements Box.BoxFactory {
    private final View view;
    private final Map<Box.BoxType, Box.OpenBoxFunction> functions;

    public BoxFactoryImpl(View view, Map<Box.BoxType, Box.OpenBoxFunction> functions){
        this.functions = functions;
        this.view = view;
    }

    @Override
    public Box produce(Point pos, Box.BoxType type) {
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        float vY = (float) (screenHeight)/426;
        switch (type){
            case UPGRADE:
                return new BoxImpl(pos, 0, vY, BitmapFactory.decodeResource(view.getResources(), R.drawable.upgrade)) {
                    @Override
                    public void openBox(Ship ship) {
                        Objects.requireNonNull(functions.get(type)).open(ship);
                    }
                };
            default:
                return new BoxImpl(pos, 0, vY, BitmapFactory.decodeResource(view.getResources(), R.drawable.heal)) {
                    @Override
                    public void openBox(Ship ship) {
                        Objects.requireNonNull(functions.get(type)).open(ship);
                    }
                };
        }
    }
}
