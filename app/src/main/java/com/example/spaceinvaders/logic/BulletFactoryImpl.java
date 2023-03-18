package com.example.spaceinvaders.logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.Bullet;

public class BulletFactoryImpl implements Bullet.BulletFactory {
    private final View view;

    public BulletFactoryImpl(View view){
        this.view = view;
    }

    @Override
    public Bullet produce(Point pos, float vX, float vY, Bullet.BulletType type) {
        Bitmap bm;
        int damage;
        switch (type){
            case COMMON:
                bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.common_bullet);
                damage = 1;
                break;
            case RARE:
                bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.rare_bullet);
                damage = 3;
                break;
            case EPIC:
                bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.epic_bullet);
                damage = 5;
                break;
            default:
                bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.villain_bullet);
                damage = 1;
        }

        return new BulletImpl(pos, vX, vY, bm, damage);
    }
}
