package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShipImpl extends ShapeImpl implements com.example.spaceinvaders.logic.interfaces.Ship{
    private final float vX;
    private final float vY;
    private final float startX;
    private final float startY;
    private int hp;
    private int barrierTime = 50;
    private final BulletsSupplier bulletsSupplier;
    private int level=0;
    private int shootCooldown=30;
    private final Map<String, Integer> info = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();
    private final ShipMoveProvider provider;

    ShipImpl(Bitmap image, Point pos, Point v, BulletsSupplier bulletsSupplier, int hp, ShipMoveProvider provider){
        super(pos, image);
        this.posX = pos.x - (float)(image.getWidth()/2);
        this.posY = pos.y - 2*image.getHeight();
        this.vX = v.x;
        this.vY = v.y;
        this.hp = hp;
        startX = posX;
        startY = posY;
        this.bulletsSupplier = bulletsSupplier;
        this.provider = provider;
    }

    public void move(boolean movable){
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Point newPos = provider.newPos(new Point((int) posX, (int) posY), new Point((int) vX, (int) vY),
                new Point(screenWidth - image.getWidth(), screenHeight - image.getHeight()), movable);
        posX = newPos.x;
        posY = newPos.y;
    }

    @Override
    public List<Bullet> shoot() {
        if(shootCooldown<=0){
            shootCooldown = 30;
            info.put("upgradeLevel", level);
            return bulletsSupplier.produce(info, (int)(posX + image.getWidth()/2), (int)posY);
        }
        shootCooldown--;
        return new ArrayList<>();
    }

    @Override
    public void upgrade() {
        if(level<8)
            level++;
    }

    @Override
    public void recenter() {
        posX = startX;
        posY = startY;
    }

    @Override
    public void changeHp(int amount) {
        if(barrierTime==0){
            hp += amount;
            notifyAllObservers("Hp: "+hp);
        }
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public boolean isAlive() {
        return hp>0;
    }

    @Override
    public void setBarrier() {
        barrierTime = 50;
    }

    @Override
    public void barrierTick() {
        if(barrierTime>0){
            barrierTime--;
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String value) {
        for(Observer observer: observers){
            observer.update(value);
        }
    }
}
