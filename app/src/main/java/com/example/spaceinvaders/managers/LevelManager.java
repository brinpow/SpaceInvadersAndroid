package com.example.spaceinvaders.managers;

import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.List;

public interface LevelManager {
    List<Villain> getNextWave(int modifier);
}
