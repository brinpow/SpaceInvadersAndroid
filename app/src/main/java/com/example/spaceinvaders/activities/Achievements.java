package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.spaceinvaders.databinding.ActivityAchievementsBinding;

public class Achievements extends AppCompatActivity {
    private ActivityAchievementsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAchievementsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}