package com.example.spaceinvaders.logic.interfaces;


import java.util.List;
import java.util.Map;

public interface BulletsSupplier {
    List<Bullet> produce(Map<String, Integer> info, int posX, int posY);
}
