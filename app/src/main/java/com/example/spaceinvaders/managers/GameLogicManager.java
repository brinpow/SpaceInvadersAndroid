package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.Iterator;
import java.util.Random;

public class GameLogicManager {
    private final GameState gameState;
    private final WaveManager waveManager;
    private final Random randomGenerator;
    private int waveNr = 0;

    GameLogicManager(GameState gameState, WaveManager waveManager){
        this.gameState = gameState;
        this.waveManager = waveManager;
        this.randomGenerator = new Random();
    }

    public void update(float[] gyroscopeValues){
        if(gameState.getGameOver()){
            return;
        }

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        if(gameState.getVillainList().size()==0){
            for(Villain villain: waveManager.getNextWave(waveNr*5)){
                gameState.getVillainList().add(villain);
            }
            gameState.getWaveNr().update("Wave: "+waveNr);
            waveNr++;
        }

        if(gameState.getMovable() && gyroscopeValues!=null){
            gameState.getShip().move(gyroscopeValues[1], gyroscopeValues[0]);
        }
        for(Bullet bullet: gameState.getShip().shoot()){
            gameState.getShipBulletList().add(bullet);
        }

        for(Villain villain: gameState.getVillainList()){
            for(Bullet bullet: villain.shoot()){
                gameState.getVillainBulletList().add(bullet);
            }
        }

        for(Bullet bullet: gameState.getShipBulletList()){
            bullet.move();
        }

        for(Bullet bullet: gameState.getVillainBulletList()){
            bullet.move();
        }

        Iterator<Bullet> itb = gameState.getShipBulletList().iterator();
        while (itb.hasNext()){
            Bullet bullet = itb.next();
            if(!bullet.isOnScreen(screenWidth, screenHeight))
                itb.remove();
        }

        itb = gameState.getVillainBulletList().iterator();
        while (itb.hasNext()){
            Bullet bullet = itb.next();
            if(!bullet.isOnScreen(screenWidth, screenHeight))
                itb.remove();
        }

        itb = gameState.getShipBulletList().iterator();

        int i =0;
        while(itb.hasNext()){
            Bullet bullet = itb.next();
            boolean isHit = false;

            Iterator<Villain> itv = gameState.getVillainList().iterator();
            while (itv.hasNext()){
                Villain villain = itv.next();
                if(bullet.intersects(villain)){
                    isHit = true;
                    villain.dealDamage(bullet.getDamageValue());
                    if(!villain.isAlive()){
                        gameState.getPlayer().updateHighScore(villain.getScore());
                        if(randomGenerator.nextInt(10)==0){
                            Point pos = villain.getPosition();
                            if(randomGenerator.nextBoolean()){
                                gameState.getBoxesList().add(gameState.getBoxFactory().produce(pos, Box.BoxType.UPGRADE));
                            }
                            else{
                                gameState.getBoxesList().add(gameState.getBoxFactory().produce(pos, Box.BoxType.HEAL));
                            }
                        }
                        itv.remove();
                    }
                    break;
                }
            }

            if(isHit){
                itb.remove();
            }
        }

        itb = gameState.getVillainBulletList().iterator();
        while (itb.hasNext()){
            Bullet bullet = itb.next();
            if(bullet.intersects(gameState.getShip())){
                gameState.getPlayer().changeHp(-1);
                gameState.getPlayer().setBarrier();
                itb.remove();
            }
        }

        for(Box box: gameState.getBoxesList()){
            box.move();
        }

        Iterator<Box> itBox = gameState.getBoxesList().iterator();
        while (itBox.hasNext()){
            if(!itBox.next().isOnScreen(screenWidth, screenHeight))
                itBox.remove();
        }

        itBox = gameState.getBoxesList().iterator();
        while (itBox.hasNext()){
            Box box = itBox.next();
            if(box.intersects(gameState.getShip())){
                box.openBox();
                itBox.remove();
            }
        }

        gameState.getPlayer().barrierTick();

        for (Villain villain : gameState.getVillainList()) {
            if (villain.intersects(gameState.getShip())) {
                gameState.getPlayer().changeHp(-1);
                gameState.getPlayer().setBarrier();
                gameState.getShip().recenter();
            }
        }

        for(Villain villain: gameState.getVillainList()){
            villain.move();
        }


        gameState.getScore().update("Score: "+gameState.getPlayer().getHighScore());
        gameState.getHp().update("Hp: "+gameState.getPlayer().getHp());

        if(!gameState.getPlayer().isAlive()){
            gameState.setGameOver(true);
        }


        //TODO ship movement, music, pause?, menu lag after back,
        // achievements, data base, high scores, multiplayer, bluetooth, observers strings
    }
}
