package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.SurfaceView;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.gui.TextImpl;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Player;
import com.example.spaceinvaders.logic.interfaces.Ship;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateImpl implements GameState {
    private final Ship ship;
    private final List<Bullet> shipBulletList;
    private final List<Bullet> villainBulletList;
    private final List<Villain> villainList;
    private final List<Box> boxList;
    private final Player player;
    private final Box.BoxFactory boxFactory;
    private final TextImpl score;
    private final TextImpl hp;
    private final TextImpl wave;
    private boolean movable;
    private boolean gameOver=false;

    public GameStateImpl(SurfaceView view){
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        Bullet.BulletFactory bulletFactory = new BulletFactoryImpl(view);
        BulletsSupplier shipBulletsSupplier = new ShipBulletSupplierImpl(bulletFactory);
        ship = new ShipImpl(BitmapFactory.decodeResource(view.getResources(), R.drawable.spaceship), new Point(screenWidth /2, screenHeight), new Point(75,75), shipBulletsSupplier);
        shipBulletList = new ArrayList<>();
        villainBulletList = new ArrayList<>();
        villainList = new ArrayList<>();
        player = new PlayerImpl(20);
        score = new TextImpl("Score: "+player.getHighScore());
        hp = new TextImpl("Hp: "+player.getHp());
        wave = new TextImpl("Wave: 0");
        boxList = new ArrayList<>();
        Map<Box.BoxType, Box.OpenBoxFunction> openBoxMap = new HashMap<>();
        openBoxMap.put(Box.BoxType.UPGRADE, ()->{
            Counter.increase(Counter.AchievementType.UPGRADES, 1);
            ship.upgrade();
        });
        openBoxMap.put(Box.BoxType.HEAL, ()->{
            Counter.increase(Counter.AchievementType.HEAL, 1);
            player.changeHp(1);});
        boxFactory = new BoxFactoryImpl(view,openBoxMap);
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public List<Bullet> getShipBulletList() {
        return shipBulletList;
    }

    @Override
    public List<Bullet> getVillainBulletList() {
        return villainBulletList;
    }

    @Override
    public List<Villain> getVillainList() {
        return villainList;
    }

    @Override
    public List<Box> getBoxesList() {
        return boxList;
    }

    @Override
    public Box.BoxFactory getBoxFactory() {
        return boxFactory;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public TextImpl getScore() {
        return score;
    }

    @Override
    public TextImpl getHp() {
        return hp;
    }

    @Override
    public TextImpl getWaveNr() {
        return wave;
    }

    @Override
    public void setMovable(boolean value) {
        movable = value;
    }

    @Override
    public boolean getMovable() {
        return movable;
    }

    @Override
    public boolean getGameOver() {
        return gameOver;
    }

    @Override
    public void setGameOver(boolean value) {
        gameOver = value;
    }
}
