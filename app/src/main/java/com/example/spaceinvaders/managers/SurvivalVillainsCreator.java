package com.example.spaceinvaders.managers;

import android.content.res.Resources;

import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SurvivalVillainsCreator implements VillainsCreator{
    private final Villain.VillainFactory villainFactory;
    private final Villain.ShootFunction shootFunction;
    private final Random rand;

    public SurvivalVillainsCreator(Villain.VillainFactory villainFactory, Villain.ShootFunction shootFunction, int seed){
        this.villainFactory = villainFactory;
        this.shootFunction = shootFunction;
        rand = new Random(seed);
    }

    @Override
    public List<Villain> assignVillainsToPath(Path path, int modifier) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        List<Villain> villainList = new ArrayList<>();
        List<Villain.VillainType> villainTypes = Arrays.asList(Villain.VillainType.values());
        int villains = path.getSize()/(screenWidth/18)+1;
        for(int i=0;i<villains;i++){
            Villain villain = villainFactory.produce(villainTypes.get(rand.nextInt(7)),modifier,path, shootFunction);
            villain.move((screenWidth/19)*i);
            villainList.add(villain);
        }
        return villainList;
    }
}
