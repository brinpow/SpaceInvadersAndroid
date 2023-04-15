package com.example.spaceinvaders.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.bluetooth.TransferService;
import com.example.spaceinvaders.database.AppDataBase;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.managers.GameView;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private MediaPlayer mediaPlayer;
    private GameView.GameType type;


    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getSupportActionBar()).hide();
        String mode = getIntent().getStringExtra("mode");
        type = GameView.GameType.valueOf(mode);

        gameView = new GameView(this, type);
        setContentView(gameView);
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
        if(type.equals(GameView.GameType.NORMAL)||type.equals(GameView.GameType.SURVIVAL)){
            Counter.updateAchievements(AppDataBase.getDB(this));
        }
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(type.equals(GameView.GameType.NORMAL)||type.equals(GameView.GameType.SURVIVAL)){
            Counter.updateHighScores(AppDataBase.getDB(this), 5);
        }

        if(type.equals(GameView.GameType.HOST)|| type.equals(GameView.GameType.CLIENT)){
            TransferService.close();
        }

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