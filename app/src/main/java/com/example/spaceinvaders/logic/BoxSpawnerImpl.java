package com.example.spaceinvaders.logic;

import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Box;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoxSpawnerImpl implements Box.BoxSpawner {
    private final Random random;
    private final Box.BoxFactory boxFactory;

    public BoxSpawnerImpl(Box.BoxFactory boxFactory, int seed){
        this.boxFactory = boxFactory;
        random = new Random(seed);
    }

    @Override
    public List<Box> create(Point pos) {
        List<Box> boxes = new ArrayList<>();
        if (random.nextInt(3) == 0) {
            if (random.nextBoolean()) {
                boxes.add(boxFactory.produce(pos, Box.BoxType.UPGRADE));
            } else {
                boxes.add(boxFactory.produce(pos, Box.BoxType.HEAL));
            }
        }
        return boxes;
    }
}
