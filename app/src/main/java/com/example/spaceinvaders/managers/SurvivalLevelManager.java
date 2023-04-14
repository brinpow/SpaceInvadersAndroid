package com.example.spaceinvaders.managers;

import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SurvivalLevelManager implements LevelManager{
    private final WaveCreator waveCreator;
    private final VillainsCreator villainsCreator;
    private int waveCooldown = 10;
    private final Random rand;


    public SurvivalLevelManager(WaveCreator waveCreator, VillainsCreator villainsCreator, int seed){
        this.waveCreator = waveCreator;
        this.villainsCreator = villainsCreator;
        rand = new Random(seed);
    }

    @Override
    public List<Villain> getNextWave(int modifier) {
        if(waveCooldown>0){
            waveCooldown--;
            return new ArrayList<>();
        }
        waveCooldown = 100;
        int waveNr = rand.nextInt(11);
        return waveCreator.getWave(modifier, waveNr, villainsCreator);
    }
}
