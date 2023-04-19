package com.example.spaceinvaders.database;

import static java.lang.Math.max;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Counter {
    public enum AchievementType{
        GAMES("You have played ", " game(s)"), HEAL("You have healed ", " health point(s)"),
        UPGRADES("You have upgraded your ship ", " time(s)"), VILLAIN("You have slain ", " monster(s)"),
        SCORE("You have earned "," score points");

        private final String first;
        private final String end;
        AchievementType(String first, String end) {
            this.first = first;
            this.end = end;
        }

        public String getAchievementText(int amount)
        {
            return first + amount + end;
        }
    }

    private static final Map<AchievementType, Integer> counter = new HashMap<>();
    private static int score;
    private static final String TAG = Counter.class.getSimpleName();
    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 6, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    @SuppressWarnings("all")
    public static void increase(AchievementType type, int amount) {
        int value = counter.getOrDefault(type, 0);
        value += amount;
        counter.put(type, value);
    }

    public static void increaseScore(int amount){
        score += amount;
    }

    public static void initializeAchievements(AppDataBase db){
        SingleObserver<List<Achievement>> getAchievementsObserver = new SingleObserver<>() {
            private final AchievementDao achievementDao = db.getAchievementDao();
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onSuccess(@NonNull List<Achievement> achievements) {
                if(achievements.size()==0){
                    for(AchievementType type: AchievementType.values()){
                        Achievement achievement = new Achievement(type.toString(), 0);
                        achievementDao.insert(achievement).subscribeOn(Schedulers.from(executorService))
                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "FATAL ERROR", e);
            }
        };
        AchievementDao achievementDao = db.getAchievementDao();
        achievementDao.getAchievements().subscribeOn(Schedulers.from(executorService))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(getAchievementsObserver);
    }

    @SuppressWarnings("all")
    public static void updateAchievements(AppDataBase db) {
        AchievementDao achievementDao = db.getAchievementDao();
        for(AchievementType type:counter.keySet()) {
            achievementDao.getAchievement(type.toString()).subscribeOn(Schedulers.from(executorService))
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(oldState -> {
                        achievementDao.updateRecord(oldState.getName(), oldState.getValue()+counter.getOrDefault(type, 0))
                                .subscribeOn(Schedulers.from(executorService)).observeOn(AndroidSchedulers.mainThread()).subscribe();
                        counter.put(type, 0);
                    }, throwable -> Log.e(TAG, "FATAL ERROR", throwable));
        }
    }

    public static void updateHighScores(AppDataBase db, int max){
        SingleObserver<List<HighScore>> getHighScoresObserver = new SingleObserver<>() {
            private final HighScoresDao highScoresDao = db.getHighScoresDao();
            private final int hScore = Counter.score;

            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onSuccess(@NonNull List<HighScore> highScores) {
                HighScore highScore = new HighScore(0, hScore);
                if (highScores.size() < max) {
                    highScore.setId(highScores.size() + 1);
                    highScoresDao.insert(highScore).subscribeOn(Schedulers.from(executorService))
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                } else {
                    HighScore worstHighScore = highScores.get(highScores.size() - 1);
                    if (worstHighScore.getHighScore() < highScore.getHighScore()) {
                        highScoresDao.delete(highScores.get(highScores.size() - 1)).subscribeOn(Schedulers.from(executorService))
                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                        int maxNr = 1;
                        for (HighScore score : highScores) {
                            maxNr = max(score.getId(), maxNr);
                        }
                        highScore.setId(maxNr + 1);
                        highScoresDao.insert(highScore).subscribeOn(Schedulers.from(executorService))
                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "FATAL ERROR", e);
            }
        };

        HighScoresDao highScoresDao = db.getHighScoresDao();
        highScoresDao.getHighScores().subscribeOn(Schedulers.from(executorService)).observeOn(AndroidSchedulers.mainThread()).subscribe(getHighScoresObserver);
        score = 0;
    }

    public static void shutDown(){
        executorService.shutdown();
    }


}
