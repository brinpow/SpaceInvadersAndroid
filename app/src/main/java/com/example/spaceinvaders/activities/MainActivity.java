package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spaceinvaders.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button[] buttons = {binding.newGame, binding.multiplayer, binding.settings, binding.achievements};
        Class<?>[] classes = {NewGame.class, Multiplayer.class, Settings.class, Achievements.class};

        for(int i=0; i<4; i++){
            int fi = i;
            buttons[i].setOnClickListener((v)->{
                Intent intent = new Intent(this, classes[fi]);
                startActivity(intent);
            });
        }

        binding.exit.setOnClickListener((v)->{
            android.os.Process.killProcess(android.os.Process.myPid());
        });

    }
}