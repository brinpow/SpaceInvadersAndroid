package com.example.spaceinvaders.logic;

import android.view.SurfaceView;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.gui.TextImpl;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Observable;
import com.example.spaceinvaders.logic.interfaces.Observer;
import com.example.spaceinvaders.logic.interfaces.Ship;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateImpl implements GameState, Observable {
    private final List<Ship> ships;
    private final List<Bullet> shipBulletList = new ArrayList<>();
    private final List<Bullet> villainBulletList = new ArrayList<>();
    private final List<Villain> villainList = new ArrayList<>();
    private final List<Box> boxList = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private final TextImpl score;
    private final List<TextImpl> hps = new ArrayList<>();
    private final TextImpl wave;
    private boolean movable;
    private boolean gameOver=false;
    private boolean youWon = false;
    private int scoreValue=0;

    public GameStateImpl(SurfaceView view, List<Ship> ships){
        this.ships = ships;
        score = new TextImpl("Score: "+getHighScore());
        addObserver(score);
        for(Ship ship: ships){
            hps.add(new TextImpl("Hp: "+ship.getHp()));
        }
        for(int i=0;i<ships.size();i++){
            ships.get(i).addObserver(hps.get(i));
        }
        wave = new TextImpl("Wave: 0");
    }

    @Override
    public List<Ship> getShips() {
        return ships;
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
    public TextImpl getScore() {
        return score;
    }

    @Override
    public List<TextImpl> getHps() {
        return hps;
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

    @Override
    public boolean getYouWon() {
        return youWon;
    }

    @Override
    public void setYouWon(boolean value) {
        youWon = value;
    }

    @Override
    public int getHighScore() {
        return scoreValue;
    }

    @Override
    public void updateHighScore(int amount) {
        Counter.increase(Counter.AchievementType.SCORE, amount);
        Counter.increaseScore(amount);
        scoreValue += amount;
        notifyAllObservers("Score: "+scoreValue);
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
