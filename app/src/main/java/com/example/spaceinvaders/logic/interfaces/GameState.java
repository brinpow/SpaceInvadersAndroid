package com.example.spaceinvaders.logic.interfaces;

import com.example.spaceinvaders.gui.TextImpl;

import java.util.List;

public interface GameState {
    Ship getShip();
    List<Bullet> getShipBulletList();
    List<Bullet> getVillainBulletList();
    List<Villain> getVillainList();
    List<Box> getBoxesList();
    Box.BoxFactory getBoxFactory();
    Player getPlayer();
    TextImpl getScore();
    TextImpl getHp();
    TextImpl getWaveNr();
    void setMovable(boolean value);
    boolean getMovable();
    boolean getGameOver();
    void setGameOver(boolean value);
}
