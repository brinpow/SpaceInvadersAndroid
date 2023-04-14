package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Ship;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.Iterator;
import java.util.List;

public class GameLogicManager {
    private final GameState gameState;
    private final LevelManager levelManager;
    private final Box.BoxSpawner boxSpawner;
    private int waveNr = 0;

    GameLogicManager(GameState gameState, LevelManager levelManager, Box.BoxSpawner boxSpawner){
        this.gameState = gameState;
        this.levelManager = levelManager;
        this.boxSpawner = boxSpawner;
    }

    public void update(){
        if(gameState.getGameOver() || gameState.getYouWon()){
            return;
        }

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        if(gameState.getVillainList().size()==0){
            List<Villain> villains = levelManager.getNextWave(waveNr*5);
            if(villains!=null){
                for(Villain villain: villains){
                    gameState.getVillainList().add(villain);
                }
            }
            else{
                gameState.setYouWon(true);
                return;
            }

            if(gameState.getVillainList().size()!=0){
                waveNr++;
                gameState.getWaveNr().update("Wave: "+waveNr);
            }
        }

        for(Ship ship: gameState.getShips()){
            ship.move(gameState.getMovable());
        }

        for(Ship ship: gameState.getShips()){
            for(Bullet bullet: ship.shoot()){
                gameState.getShipBulletList().add(bullet);
            }
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
                        Counter.increase(Counter.AchievementType.VILLAIN, 1);
                        gameState.updateHighScore(villain.getScore());
                        Point pos = villain.getPosition();
                        for(Box box:boxSpawner.create(pos)){
                            gameState.getBoxesList().add(box);
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
            for(Ship ship: gameState.getShips()){
                if(bullet.intersects(ship)){
                    ship.changeHp(-1);
                    ship.setBarrier();
                    itb.remove();
                }
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
            for(Ship ship: gameState.getShips()){
                if(box.intersects(ship)){
                    box.openBox(ship);
                    itBox.remove();
                }
            }
        }

        for(Ship ship: gameState.getShips()){
            ship.barrierTick();
        }

        for (Villain villain : gameState.getVillainList()) {
            for(Ship ship: gameState.getShips()){
                if (villain.intersects(ship)) {
                    ship.changeHp(-1);
                    ship.setBarrier();
                    ship.recenter();
                }
            }
        }

        for(Villain villain: gameState.getVillainList()){
            villain.move();
        }

        gameState.getShips().removeIf(ship -> !ship.isAlive()&&gameState.getShips().size()!=1);
        if(gameState.getShips().size()==1 && !gameState.getShips().get(0).isAlive()){
            gameState.setGameOver(true);
        }


        //TODO maybe create special shooting boss, maybe refactor achievements - normal/ survival/ multiplayer distinction, refactor path constants/villain velocity
    }
}
