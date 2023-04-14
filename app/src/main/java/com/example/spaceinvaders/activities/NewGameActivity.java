package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.spaceinvaders.databinding.ActivityNewGameBinding;
import com.example.spaceinvaders.managers.GameView;

import java.util.Objects;

public class NewGameActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewGameBinding binding = ActivityNewGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding.survival.setOnClickListener((v)->{
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("mode", "SURVIVAL");
            startActivity(intent);});
        binding.normal.setOnClickListener((v)->{
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("mode", "NORMAL");
            startActivity(intent);});
    }
}