package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.database.AppDataBase;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.databinding.ActivityAchievementsBinding;

import java.util.Objects;

public class AchievementsActivity extends AppCompatActivity {

    @SuppressWarnings("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAchievementsBinding binding = ActivityAchievementsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AppDataBase db = AppDataBase.getDB(this);
        Counter.initializeAchievements(db);

        RecyclerView highScoresView = findViewById(R.id.highScoreView);
        RecyclerView achievementsView = findViewById(R.id.achievementsView);
        highScoresView.setAdapter(new HighScoresViewAdapter(this, db.getHighScoresDao()));
        achievementsView.setAdapter(new AchievementsViewAdapter(this, db.getAchievementDao()));
    }
}