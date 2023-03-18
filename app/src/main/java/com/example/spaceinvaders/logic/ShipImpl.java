package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShipImpl extends ShapeImpl implements com.example.spaceinvaders.logic.interfaces.Ship{
    private final float vX;
    private final float vY;
    private final float startX;
    private final float startY;
    private final BulletsSupplier bulletsSupplier;
    private int level;
    private int shootCooldown;
    private final Map<String, Integer> info;

    public ShipImpl(Bitmap image, Point pos, Point v, BulletsSupplier bulletsSupplier){
        super(pos, image);
        this.posX = pos.x - (float)(image.getWidth()/2);
        this.posY = pos.y - 2*image.getHeight();
        this.vX = v.x;
        this.vY = v.y;
        startX = posX;
        startY = posY;
        this.bulletsSupplier = bulletsSupplier;
        level = 0;
        shootCooldown = 30;
        info = new HashMap<>();
    }

    private float movePose(float pX, float x, float vX, float limit){
        if(Math.abs(x)>0.15) {
            if (Math.abs(x) < 0.35) {
                pX += x * vX;
            } else {
                pX += x * vX / 3;
            }
            pX = Math.min(Math.max(pX, 0), limit);
            return pX;
        }
        return pX;
    }

    @SuppressWarnings("All")
    public void move(float x, float y){
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        posX = movePose(posX, x, vX, screenWidth - image.getWidth());
        posY = movePose(posY, y, vY,screenHeight - image.getHeight());
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
}
