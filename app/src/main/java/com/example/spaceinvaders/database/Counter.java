package com.example.spaceinvaders.database;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        AchievementDao achievementDao = db.getAchievementDao();
        List<Achievement> achievements = achievementDao.getAllAchievements();

        if(achievements.size()==0){
            for(AchievementType type: AchievementType.values()){
                Achievement achievement = new Achievement(type.toString(), 0);
                achievementDao.insert(achievement);
            }
        }
    }

    @SuppressWarnings("all")
    public static void updateAchievements(AppDataBase db) {
        AchievementDao achievementDao = db.getAchievementDao();
        for(AchievementType type:counter.keySet()) {
            Achievement oldState = achievementDao.getAchievement(type.toString());
            achievementDao.updateRecord(oldState.getName(), oldState.getValue()+counter.getOrDefault(type, 0));
        }
        counter.clear();
    }

    public static void updateHighScores(AppDataBase db, int max){
        HighScoresDao highScoresDao = db.getHighScoresDao();
        List<HighScore> highScores = highScoresDao.getAllHighScores();
        HighScore highScore = new HighScore(0, score);
        if(highScores.size()<max){
            highScore.setId(highScores.size()+1);
            highScoresDao.insert(highScore);
        }
        else{
            HighScore worstHighScore = highScores.get(highScores.size()-1);
            if(worstHighScore.getHighScore()<highScore.getHighScore()){
                highScoresDao.delete(highScores.get(highScores.size()-1));
                int maxNr = 1;
                for(HighScore score: highScores){
                    maxNr = max(score.getId(), maxNr);
                }
                highScore.setId(maxNr+1);
                highScoresDao.insert(highScore);
            }
        }
        score = 0;
    }


}
