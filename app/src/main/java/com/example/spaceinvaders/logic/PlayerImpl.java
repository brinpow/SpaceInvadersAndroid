package com.example.spaceinvaders.logic;

public class PlayerImpl implements com.example.spaceinvaders.logic.interfaces.Player {

    private int hp;
    private int score;
    private int barrierTime = 50;

    PlayerImpl(int hp){
        this.hp = hp;
        score = 0;
    }

    @Override
    public void changeHp(int amount) {
        if(barrierTime==0){
            hp += amount;
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
    public int getHighScore() {
        return score;
    }

    @Override
    public void updateHighScore(int amount) {
        score += amount;
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
}
