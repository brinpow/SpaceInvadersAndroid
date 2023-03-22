package com.example.spaceinvaders.managers;

import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.List;

public interface WaveManager {
    List<Villain> getNextWave(int modifier);
}
