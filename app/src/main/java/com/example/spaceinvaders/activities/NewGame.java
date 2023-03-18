package com.example.spaceinvaders.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.example.spaceinvaders.managers.GameView;

public class NewGame extends Activity {
    private GameView gameView;


    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        setContentView(gameView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            gameView.screenContact(event.getX(), event.getY(), true);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            gameView.screenContact(event.getX(), event.getY(), false);
        }
        return super.onTouchEvent(event);
    }
}