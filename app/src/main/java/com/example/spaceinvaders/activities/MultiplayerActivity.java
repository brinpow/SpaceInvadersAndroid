package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.spaceinvaders.databinding.ActivityMultiplayerBinding;

public class MultiplayerActivity extends AppCompatActivity {
    private ActivityMultiplayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}