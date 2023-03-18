package com.example.spaceinvaders.logic;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Villain;

public class VillainFactoryImpl implements Villain.VillainFactory {
    private final View view;

    public VillainFactoryImpl(View view){
        this.view = view;
    }

    @Override
    public Villain produce(Point pos, Villain.VillainType type, BulletsSupplier bulletsSupplier) {
        switch (type){
            case v1:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain1),5, type, bulletsSupplier);
            case v2:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain2),10, type, bulletsSupplier);
            case v3:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain9),20, type, bulletsSupplier);
            case v4:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain8),35, type, bulletsSupplier);
            case v5:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain4),50, type, bulletsSupplier);
            case v6:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain13),75, type, bulletsSupplier);
            default:
                return new VillainImpl(pos, BitmapFactory.decodeResource(view.getResources(), R.drawable.villain11),100, type, bulletsSupplier);
        }
    }
}
