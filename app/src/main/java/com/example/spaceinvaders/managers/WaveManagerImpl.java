package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WaveManagerImpl implements WaveManager{
    private final Path.PathFactory pathFactory;
    private final Villain.VillainFactory villainFactory;
    private final BulletsSupplier bulletsSupplier;
    private final Random rand;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int waveCooldown = 0;

    public WaveManagerImpl(Villain.VillainFactory villainFactory, Path.PathFactory pathFactory, BulletsSupplier bulletsSupplier){
        this.villainFactory = villainFactory;
        this.pathFactory = pathFactory;
        this.bulletsSupplier = bulletsSupplier;
        rand = new Random();
    }

    private List<Villain> createSimpleWave(Point pos, int modifier, int value){
        List<Villain> villainList = new ArrayList<>();
        List<Villain.VillainType> villainTypes = Arrays.asList(Villain.VillainType.values());
        Path path = pathFactory.produce(Path.PathType.SMALL_RECT, pos);
        int villains;

        switch (value){
            case 0:
                path = pathFactory.produce(Path.PathType.SMALL_RECT, pos);
                break;
            case 1:
                path = pathFactory.produce(Path.PathType.BIG_RECT, pos);
                break;
            case 2:
                path = pathFactory.produce(Path.PathType.TRIANGLE, pos);
                break;
            case 3:
                path = pathFactory.produce(Path.PathType.DIAMOND, pos);
                break;
            case 4:
                path = pathFactory.produce(Path.PathType.DTRIANGLE, pos);
                break;
            case 5:
                path = pathFactory.produce(Path.PathType.HEXAGON, pos);
                break;
            case 6:
                path = pathFactory.produce(Path.PathType.STABLE, pos);
                break;
        }

        villains = path.getSize()/60+1;
        for(int i=0;i<villains;i++){
            Villain villain = villainFactory.produce(villainTypes.get(rand.nextInt(7)),modifier, bulletsSupplier,path);
            villain.move(55*i);
            villainList.add(villain);
        }
        return villainList;
    }
    
    @Override
    public List<Villain> getNextWave(int modifier) {
        List<Villain> villainList = new ArrayList<>();
        if(waveCooldown>0){
            waveCooldown--;
            return villainList;
        }
        waveCooldown = 100;
        int waveNr = rand.nextInt(11);
        switch (waveNr){
            case 0:
                return createSimpleWave(new Point(), modifier, 0);
            case 1:
                return createSimpleWave(new Point(), modifier, 1);
            case 2:
                return createSimpleWave(new Point(), modifier, 2);
            case 3:
                return createSimpleWave(new Point(), modifier, 3);
            case 4:
                return createSimpleWave(new Point(), modifier, 4);
            case 5:
                return createSimpleWave(new Point(), modifier, 5);
            case 6:
                villainList = createSimpleWave(new Point(), modifier,0);
                villainList.addAll(createSimpleWave(new Point(0,500),modifier, 0));
                return villainList;
            case 7:
                villainList = createSimpleWave(new Point(), modifier, 1);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 450),modifier,6));
                return villainList;
            case 8:
                villainList = createSimpleWave(new Point(), modifier, 3);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 500),modifier,6));
                return villainList;
            case 9:
                villainList = createSimpleWave(new Point(), modifier, 5);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 500),modifier, 6));
                return villainList;
            case 10:
                villainList = createSimpleWave(new Point(), modifier, 2);
                villainList.addAll(createSimpleWave(new Point(0, 600),modifier, 0));
                return villainList;
            case 11:
                villainList = createSimpleWave(new Point(), modifier, 0);
                villainList.addAll(createSimpleWave(new Point(0, 500),modifier,4));
                return villainList;
        }
        return villainList;
    }
}
