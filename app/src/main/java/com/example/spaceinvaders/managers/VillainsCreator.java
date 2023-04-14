package com.example.spaceinvaders.managers;

import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.util.List;

public interface VillainsCreator {
    List<Villain> assignVillainsToPath(Path path, int modifier);
}
