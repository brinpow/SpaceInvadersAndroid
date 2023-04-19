package com.example.spaceinvaders.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.database.AppDataBase;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.databinding.ActivityAchievementsBinding;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AchievementsActivity extends AppCompatActivity {
    private static final String TAG = Counter.class.getSimpleName();
    private final ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 6, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

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

        RecyclerView highScoresView = findViewById(R.id.highScoreView);
        RecyclerView achievementsView = findViewById(R.id.achievementsView);

        HighScoresViewAdapter highScoresAdapter = new HighScoresViewAdapter(this);
        highScoresView.setAdapter(highScoresAdapter);
        db.getHighScoresDao().getAllHighScores().subscribeOn(Schedulers.from(executorService)).observeOn(AndroidSchedulers.mainThread())
                .subscribe(highScores -> highScoresAdapter.setHighScores(highScores),throwable -> Log.e(TAG, "FATAL ERROR", throwable));

        AchievementsViewAdapter achievementsAdapter = new AchievementsViewAdapter(this);
        achievementsView.setAdapter(achievementsAdapter);
        db.getAchievementDao().getAllAchievements().subscribeOn(Schedulers.from(executorService)).observeOn(AndroidSchedulers.mainThread())
                .subscribe(achivements -> achievementsAdapter.setAchievements(achivements),throwable -> Log.e(TAG, "FATAL ERROR", throwable));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}