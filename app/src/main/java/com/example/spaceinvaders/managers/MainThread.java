package com.example.spaceinvaders.managers;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    private final GameView gameView;
    private final SurfaceHolder holder;
    private Canvas canvas;
    private boolean gameRunning;

    public MainThread(SurfaceHolder holder, GameView view){
        this.holder = holder;
        this.gameView = view;
    }

    public void setRunning(boolean isGameRunning){
        gameRunning = isGameRunning;
    }

    @Override
    public void run(){
        while(gameRunning){
            canvas = null;

            try{
                gameView.update();
                canvas = holder.lockCanvas();
                synchronized (holder){
                    gameView.draw(canvas);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if(canvas!=null){
                    try{
                        holder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
