package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Villain;

public class DrawManager {
    private final GameState gameState;

    DrawManager(GameState gameState){
        this.gameState = gameState;
    }

    public void draw(Canvas canvas){
        this.gameState.getShip().draw(canvas);

        for(Bullet bullet: gameState.getShipBulletList()){
            bullet.draw(canvas);
        }

        for(Bullet bullet: gameState.getVillainBulletList()){
            bullet.draw(canvas);
        }

        for(Villain villain: gameState.getVillainList()){
            villain.draw(canvas);
        }

        for(Box box: gameState.getBoxesList()){
            box.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(75);
        canvas.drawText(gameState.getScore().getText(), 50, paint.getTextSize(), paint);
        paint.setColor(Color.RED);
        canvas.drawText(gameState.getHp().getText(), Resources.getSystem().getDisplayMetrics().widthPixels - 250,paint.getTextSize(),paint);
        paint.setColor(Color.BLUE);
        canvas.drawText(gameState.getWaveNr().getText(), (float) (Resources.getSystem().getDisplayMetrics().widthPixels/2) - 100,paint.getTextSize(),paint);
    }
}
