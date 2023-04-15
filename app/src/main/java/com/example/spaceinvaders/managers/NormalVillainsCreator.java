package com.example.spaceinvaders.managers;

import android.content.Context;
import android.content.res.Resources;

import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NormalVillainsCreator implements VillainsCreator{
    private final Villain.VillainFactory villainFactory;
    private final Villain.ShootFunction shootFunction;
    private final DataInputStream stream;

    public NormalVillainsCreator(Context context, Villain.VillainFactory villainFactory, Villain.ShootFunction shootFunction){
        this.villainFactory = villainFactory;
        this.shootFunction = shootFunction;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("villain_numbers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream = new DataInputStream(inputStream);
    }
    @Override
    public List<Villain> assignVillainsToPath(Path path, int modifier) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        List<Villain> villainList = new ArrayList<>();
        List<Villain.VillainType> villainTypes = Arrays.asList(Villain.VillainType.values());
        int villains = path.getSize()/(screenWidth/18)+1;
        for(int i=0;i<villains;i++){
            int villainNr;
            try {
                villainNr = stream.readInt();
            } catch (Exception e) {
                e.printStackTrace();
                if(i!=0){
                    return villainList;
                }
                return null;
            }
            Villain villain = villainFactory.produce(villainTypes.get(villainNr),modifier,path, shootFunction);
            villain.move((screenWidth/19)*i);
            villainList.add(villain);
        }
        return villainList;
    }
}
