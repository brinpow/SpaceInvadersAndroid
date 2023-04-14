package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spaceinvaders.databinding.ActivityMainBinding;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.spaceinvaders.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button[] buttons = {binding.newGame, binding.multiplayer, binding.achievements};
        Class<?>[] classes = {NewGameActivity.class, MultiplayerActivity.class, AchievementsActivity.class};

        for(int i=0; i<3; i++){
            int fi = i;
            buttons[i].setOnClickListener((v)->{
                Intent intent = new Intent(this, classes[fi]);
                startActivity(intent);
            });
        }

        binding.exit.setOnClickListener((v)-> finishAndRemoveTask());

    }
}