package com.example.spaceinvaders.logic.interfaces;

public interface Player {
    void changeHp(int amount);
    int getHp();
    boolean isAlive();
    int getHighScore();
    void updateHighScore(int amount);
    void setBarrier();
    void barrierTick();
}
