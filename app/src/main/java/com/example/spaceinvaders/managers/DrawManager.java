package com.example.spaceinvaders.managers;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.spaceinvaders.gui.Text;
import com.example.spaceinvaders.gui.TextImpl;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Villain;

public class DrawManager {
    private final GameState gameState;
    private final Text gameOver = new TextImpl("GAME OVER");

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

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(75);
        canvas.drawText(gameState.getScore().getText(), 50, paint.getTextSize(), paint);
        paint.setColor(Color.RED);
        canvas.drawText(gameState.getHp().getText(), screenWidth - 250,paint.getTextSize(),paint);
        paint.setColor(Color.BLUE);
        canvas.drawText(gameState.getWaveNr().getText(), (float) (screenWidth/2) - 100,paint.getTextSize(),paint);

        if(gameState.getGameOver()){
            paint.setColor(Color.RED);
            paint.setTextSize(150);
            canvas.drawText(gameOver.getText(), 125,(float)(screenHeight/2),paint);
        }
    }
}
