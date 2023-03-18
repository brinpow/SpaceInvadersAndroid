package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.spaceinvaders.databinding.ActivityMultiplayerBinding;
import com.example.spaceinvaders.databinding.ActivitySettingsBinding;

public class Multiplayer extends AppCompatActivity {
    private ActivityMultiplayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}