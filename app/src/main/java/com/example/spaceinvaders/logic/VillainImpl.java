package com.example.spaceinvaders.logic;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VillainImpl extends ShapeImpl implements com.example.spaceinvaders.logic.interfaces.Villain {
    private int hp;
    private final VillainType type;
    private final BulletsSupplier bulletsSupplier;
    private int shootCooldown = 30;
    private final Random random = new Random();
    private final Path path;
    private int pathIndex = 0;

    public VillainImpl(Point pos, Bitmap image, int hp, VillainType type, Path path, BulletsSupplier bulletsSupplier){
        super(pos, image);
        this.hp = hp;
        this.type = type;
        this.bulletsSupplier = bulletsSupplier;
        this.path = path;
    }


    @Override
    public void dealDamage(int damage) {
        hp -= damage;
    }

    @Override
    public boolean isAlive() {
        return hp>=0;
    }

    @Override
    public void move() {
        Point newPos = path.get(pathIndex++);
        posX = newPos.x - (float)(image.getWidth()/2);
        posY = newPos.y - (float)(image.getHeight()/2);
    }

    @Override
    public Point getPosition() {
        return new Point((int)posX + image.getHeight()/2, (int)posY + image.getWidth());
    }

    @Override
    public int getScore() {
        return type.getScore();
    }

    @Override
    public List<Bullet> shoot() {
        List<Bullet> bullets = new ArrayList<>();
        if(shootCooldown <= 0 && random.nextInt()%20 == 0){
                shootCooldown = 30;
                return bulletsSupplier.produce(null, (int) (posX + image.getWidth()/2), (int) (posY+ image.getHeight()));
        }
        shootCooldown--;
        return bullets;
    }
}
