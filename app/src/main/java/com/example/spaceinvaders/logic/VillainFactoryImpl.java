package com.example.spaceinvaders.logic;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.List;

public class VillainFactoryImpl implements Villain.VillainFactory {
    private final View view;

    public VillainFactoryImpl(View view){
        this.view = view;
    }

    @Override
    public Villain produce(Villain.VillainType type, int modifier, Path path, Villain.ShootFunction shootFunction) {
        switch (type){
            case v1:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain1), 5 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            case v2:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain2), 10 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            case v3:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain9), 15 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            case v4:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain8), 20 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            case v5:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain4), 23 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            case v6:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain13), 26 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
            default:
                return new VillainImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.villain11), 30 + modifier, type, path) {
                    @Override
                    public List<Bullet> shoot() {
                        Point pos = new Point((int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
                        return shootFunction.shoot(pos);
                    }
                };
        }
    }
}
