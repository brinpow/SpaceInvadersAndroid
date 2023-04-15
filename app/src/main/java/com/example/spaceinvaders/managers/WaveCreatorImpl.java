package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaveCreatorImpl implements WaveCreator{
    private final Path.PathFactory pathFactory;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public WaveCreatorImpl(Path.PathFactory pathFactory){
        this.pathFactory = pathFactory;
    }

    private List<Villain> createSimpleWave(Point pos, int modifier, int value, VillainsCreator villainsCreator){
        Path path = pathFactory.produce(Path.PathType.SMALL_RECT, pos);

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

        return villainsCreator.assignVillainsToPath(path, modifier);
    }

    public List<Villain> getWave(int modifier, int waveNr, VillainsCreator villainsCreator) {
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int sY = screenHeight/42;
        List<Villain> villainList = new ArrayList<>();
        switch (waveNr){
            case 0:
                return createSimpleWave(new Point(), modifier, 0, villainsCreator);
            case 1:
                return createSimpleWave(new Point(), modifier, 1, villainsCreator);
            case 2:
                return createSimpleWave(new Point(), modifier, 2, villainsCreator);
            case 3:
                return createSimpleWave(new Point(), modifier, 3, villainsCreator);
            case 4:
                return createSimpleWave(new Point(), modifier, 4, villainsCreator);
            case 5:
                return createSimpleWave(new Point(), modifier, 5, villainsCreator);
            case 6:
                villainList = createSimpleWave(new Point(), modifier,0, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(0,10*sY),modifier, 0, villainsCreator));
                return villainList;
            case 7:
                villainList = createSimpleWave(new Point(), modifier, 1, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 9*sY),modifier,6, villainsCreator));
                return villainList;
            case 8:
                villainList = createSimpleWave(new Point(), modifier, 3, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 10*sY),modifier,6, villainsCreator));
                return villainList;
            case 9:
                villainList = createSimpleWave(new Point(), modifier, 5, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(screenWidth/2, 10*sY),modifier, 6, villainsCreator));
                return villainList;
            case 10:
                villainList = createSimpleWave(new Point(), modifier, 2, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(0, 12*sY),modifier, 0, villainsCreator));
                return villainList;
            case 11:
                villainList = createSimpleWave(new Point(), modifier, 0, villainsCreator);
                villainList.addAll(createSimpleWave(new Point(0, 10*sY),modifier,4, villainsCreator));
                return villainList;
        }
        return villainList;
    }
}
