package com.example.spaceinvaders.logic;

import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VillainBulletsSupplier implements BulletsSupplier {
    private final Bullet.BulletFactory factory;

    public VillainBulletsSupplier(Bullet.BulletFactory factory){
        this.factory = factory;
    }

    @Override
    public List<Bullet> produce(Map<String, Integer> info, int posX, int posY) {
        List<Bullet> bulletList = new ArrayList<>();
        bulletList.add(factory.produce(new Point(posX, posY), 0, 5, Bullet.BulletType.VILLAIN));
        return bulletList;
    }
}
