package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShipBulletSupplierImpl implements BulletsSupplier {
    private final Bullet.BulletFactory bulletFactory;
    public ShipBulletSupplierImpl(Bullet.BulletFactory bulletFactory){
        this.bulletFactory = bulletFactory;
    }

    @SuppressWarnings("all")
    @Override
    public List<Bullet> produce(Map<String, Integer> info, int posX, int posY) {
        int upgradeLevel = info.getOrDefault("upgradeLevel", 0);
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        List<Bullet> bulletList = new ArrayList<>();
        float vX =  (float) screenWidth/2160;
        float vY = -screenHeight/213;
        Bullet.BulletType[] types = {Bullet.BulletType.COMMON, Bullet.BulletType.RARE, Bullet.BulletType.EPIC};
        switch (upgradeLevel){
            case 0:
            case 3:
            case 6:
                bulletList.add(bulletFactory.produce(new Point(posX, posY), 0, vY,types[upgradeLevel/3]));
                break;
            case 1:
            case 4:
            case 7:
                bulletList.add(bulletFactory.produce(new Point(posX, posY), vX, vY,types[upgradeLevel/3]));
                bulletList.add(bulletFactory.produce(new Point(posX, posY), -vX, vY,types[upgradeLevel/3]));
                break;
            case 2:
            case 5:
            case 8:
                bulletList.add(bulletFactory.produce(new Point(posX, posY), 0, -screenHeight/177,types[upgradeLevel/3]));
                bulletList.add(bulletFactory.produce(new Point(posX, posY), -vX, vY,types[upgradeLevel/3]));
                bulletList.add(bulletFactory.produce(new Point(posX, posY), vX, vY,types[upgradeLevel/3]));
                break;
        }
        return bulletList;
    }
}
