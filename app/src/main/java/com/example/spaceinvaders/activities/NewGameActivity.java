package com.example.spaceinvaders.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.database.AppDataBase;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.managers.GameView;

public class NewGameActivity extends Activity {
    private GameView gameView;
    private MediaPlayer mediaPlayer;


    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        setContentView(gameView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Counter.increase(Counter.AchievementType.GAMES, 1);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.space_music);
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Counter.updateAchievements(AppDataBase.getDB(this));
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Counter.updateHighScores(AppDataBase.getDB(this), 5);
        mediaPlayer.stop();
        mediaPlayer.release();
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