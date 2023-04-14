package com.example.spaceinvaders.managers;

import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.List;

public interface WaveCreator {
    List<Villain> getWave(int modifier, int waveNr, VillainsCreator villainsCreator);
}
