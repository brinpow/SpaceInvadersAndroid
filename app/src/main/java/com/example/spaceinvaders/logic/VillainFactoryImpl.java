package com.example.spaceinvaders.logic;

import android.graphics.BitmapFactory;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

public class VillainFactoryImpl implements Villain.VillainFactory {
    private final View view;

    public VillainFactoryImpl(View view){
        this.view = view;
    }

    @Override
    public Villain produce(Villain.VillainType type, int modifier, BulletsSupplier bulletsSupplier, Path path) {
        switch (type){
            case v1:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain1),5+modifier, type, path, bulletsSupplier);
            case v2:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain2),10+modifier, type, path, bulletsSupplier);
            case v3:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain9),15+modifier, type, path, bulletsSupplier);
            case v4:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain8),20+modifier, type, path, bulletsSupplier);
            case v5:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain4),23+modifier, type, path, bulletsSupplier);
            case v6:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain13),26+modifier, type, path, bulletsSupplier);
            default:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain11),30+modifier, type, path, bulletsSupplier);
        }
    }
}
