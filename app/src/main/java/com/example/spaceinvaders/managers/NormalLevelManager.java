package com.example.spaceinvaders.managers;

import android.content.Context;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.logic.interfaces.Villain;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NormalLevelManager implements LevelManager{
    private final WaveCreator waveCreator;
    private final VillainsCreator villainsCreator;
    private int waveCooldown = 10;
    private final DataInputStream stream;

    public NormalLevelManager(Context context, WaveCreator waveCreator, VillainsCreator villainsCreator){
        this.waveCreator = waveCreator;
        this.villainsCreator = villainsCreator;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("wave_numbers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream = new DataInputStream(inputStream);
    }

    @Override
    public List<Villain> getNextWave(int modifier) {
        if(waveCooldown>0){
            waveCooldown--;
            return new ArrayList<>();
        }
        waveCooldown = 100;
        int waveNr;
        try {
            waveNr = stream.readInt();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return waveCreator.getWave(modifier, waveNr, villainsCreator);
    }
}
