package com.example.spaceinvaders.logic.interfaces;

import com.example.spaceinvaders.gui.TextImpl;

import java.util.List;

public interface GameState {
    List<Ship> getShips();
    List<Bullet> getShipBulletList();
    List<Bullet> getVillainBulletList();
    List<Villain> getVillainList();
    List<Box> getBoxesList();
    TextImpl getScore();
    List<TextImpl> getHps();
    TextImpl getWaveNr();
    void setMovable(boolean value);
    boolean getMovable();
    boolean getGameOver();
    void setGameOver(boolean value);
    boolean getYouWon();
    void setYouWon(boolean value);
    int getHighScore();
    void updateHighScore(int amount);
}
